package be.com.learn.adminsys.b3q1_androidproject_jm.Fragment;

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

import java.util.HashMap;
import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Controller.EvaluationAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.CompositeEvaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.FinalEvaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.NewEvaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.EvaluationParent;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class EvaluationFragment extends Fragment {

    private RecyclerView recyclerViewEvaluations;
    private EvaluationAdapter evaluationAdapter;
    private EvaluationParent selectedParent;  // Utilise EvaluationParent, peu importe si c'est NewCourse ou CompositeEvaluation

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        recyclerViewEvaluations = view.findViewById(R.id.recyclerViewEvaluations);
        recyclerViewEvaluations.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Ajouter un bouton pour afficher la popup
        Button buttonAddEvaluation = view.findViewById(R.id.addEvaluationButton);
        buttonAddEvaluation.setOnClickListener(v -> showAddEvaluationDialog(v));  // Ouvre la popup
        TextView textViewParentName = view.findViewById(R.id.textEvaluationTitle);
        // Récupère le parent sélectionné depuis les arguments
        if (getArguments() != null) {
            selectedParent = (EvaluationParent) getArguments().getSerializable("selectedParent");
        }
        if (selectedParent != null) {
            textViewParentName.setText(selectedParent.getName());
        }
        loadEvaluations();
        return view;
    }

    private void loadEvaluations() {
        if (selectedParent != null) {
            List<NewEvaluation> evaluations = selectedParent.getEvaluations();

            if (evaluations != null) {
                evaluationAdapter = new EvaluationAdapter(evaluations, evaluation -> {
                    if (evaluation instanceof CompositeEvaluation) {
                        navigateToSubEvaluations((CompositeEvaluation) evaluation);
                    }
                });
                recyclerViewEvaluations.setAdapter(evaluationAdapter);
            } else {
                Toast.makeText(requireContext(), "Aucune évaluation disponible", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(requireContext(), "Problème de chargement du parents", Toast.LENGTH_SHORT).show();
        }

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

        confirmButton.setOnClickListener(view -> {
            String evaluationName = editTextEvaluationName.getText().toString();
            String maxPointsStr = editTextMaxPoints.getText().toString();

            if (evaluationName.isEmpty() || maxPointsStr.isEmpty()) {
                Toast.makeText(requireContext(), "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            } else {
                int maxPoints = Integer.parseInt(maxPointsStr);

                // Vérifier si c'est une évaluation finale ou composite
                NewEvaluation newEvaluation;
                if (checkBoxIsFinal.isChecked()) {
                    newEvaluation = new FinalEvaluation(evaluationName, maxPoints, selectedParent.getStudents());
                } else {
                    newEvaluation = new CompositeEvaluation(evaluationName, maxPoints, selectedParent.getStudents());
                }

                selectedParent.addEvaluation(newEvaluation);  // Appelle la méthode d'ajout sur l'objet parent
                evaluationAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });

        cancelButton.setOnClickListener(view -> popupWindow.dismiss());
    }

    private void navigateToSubEvaluations(CompositeEvaluation compositeEvaluation) {
        // Naviguer vers un nouveau fragment pour afficher les sous-évaluations
        Bundle args = new Bundle();
        args.putSerializable("selectedParent", compositeEvaluation);  // Passer CompositeEvaluation comme parent
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
