package be.com.learn.adminsys.b3q1_androidproject_jm.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.EvaluationAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class EvaluationFragment extends Fragment {

    private RecyclerView recyclerViewEvaluations;
    private EvaluationAdapter evaluationAdapter;
    private AppDatabase db;
    private int parentId; // ID du parent (e.g., Course ou CompositeEvaluation)
    private String parentType; // Type du parent ("Course" ou "CompositeEvaluation")
    String parentName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        recyclerViewEvaluations = view.findViewById(R.id.recyclerViewEvaluations);
        recyclerViewEvaluations.setLayoutManager(new LinearLayoutManager(requireContext()));

        db = AppDatabase.getInstance(requireContext());

        // Récupérer les arguments
        if (getArguments() != null) {

            parentId = getArguments().getInt("parentId");
            parentType = getArguments().getString("parentType");
        }

        // Initialiser le bouton d'ajout
        Button buttonAddEvaluation = view.findViewById(R.id.addEvaluationButton);
        buttonAddEvaluation.setOnClickListener(this::showAddEvaluationDialog);

        // Mettre à jour le titre



        setParentName();
        loadEvaluations();
        return view;
    }

    private void setParentName() {
        Executors.newSingleThreadExecutor().execute(() -> {
            if ("Course".equals(parentType)) {
                parentName = "Evaluation du cours :  " + db.courseDao().getCourseById(parentId).getName();
            } else {
                parentName = "Sous-évaluation de : " + db.evaluationDao().getEvaluationById(parentId).getName();
            }

            requireActivity().runOnUiThread(() -> {
                TextView textEvaluationTitle = requireView().findViewById(R.id.textEvaluationTitle);
                textEvaluationTitle.setText(parentName);
            });
        });
    }

    private void loadEvaluations() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Evaluation> evaluations = db.evaluationDao().getEvaluationsByParent(parentId, parentType);

            requireActivity().runOnUiThread(() -> {
                evaluationAdapter = new EvaluationAdapter(evaluations, evaluation -> {
                    if ("Composite".equals(evaluation.getType())) {
                        navigateToSubEvaluations(evaluation);
                    }
                });
                recyclerViewEvaluations.setAdapter(evaluationAdapter);
            });
        });
    }

    private void showAddEvaluationDialog(View v) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View popupView = inflater.inflate(R.layout.popup_add_evaluation, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // Récupération des éléments de la popup
        EditText editTextEvaluationName = popupView.findViewById(R.id.editTextEvaluationName);
        EditText editTextMaxPoints = popupView.findViewById(R.id.editTextMaxPoints);
        CheckBox checkBoxIsFinal = popupView.findViewById(R.id.checkBoxIsFinal);
        Button confirmButton = popupView.findViewById(R.id.confirmEvaluationButton);
        Button cancelButton = popupView.findViewById(R.id.cancelEvaluationButton);
        editTextMaxPoints.setText("20");
        confirmButton.setOnClickListener(view -> {
            String evaluationName = editTextEvaluationName.getText().toString();
            String maxPointsStr = editTextMaxPoints.getText().toString();

            if (evaluationName.isEmpty() || maxPointsStr.isEmpty()) {
                Toast.makeText(requireContext(), "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            } else {
                int maxPoints = Integer.parseInt(maxPointsStr);
                String type = checkBoxIsFinal.isChecked() ? "Final" : "Composite";

                Evaluation newEvaluation = new Evaluation(maxPoints, evaluationName, parentType, parentId, type);

                Executors.newSingleThreadExecutor().execute(() -> {
                    db.evaluationDao().insert(newEvaluation);

                    requireActivity().runOnUiThread(() -> {
                        loadEvaluations();
                        popupWindow.dismiss();
                    });
                });
            }
        });

        cancelButton.setOnClickListener(view -> popupWindow.dismiss());
    }

    private void navigateToSubEvaluations(Evaluation compositeEvaluation) {
        // Naviguer vers un nouveau fragment pour afficher les sous-évaluations
        Bundle args = new Bundle();
        args.putInt("parentId", compositeEvaluation.getId());
        args.putString("parentType", "CompositeEvaluation");
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
