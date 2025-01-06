package be.com.learn.adminsys.b3q1_androidproject_jm.Views.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.EvaluationController;
import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.GradeController;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Fragment.GradeDetailFragment;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.EvaluationManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.GradeManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.StudentManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Adapter.EvaluationAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Adapter.GradeAdapter;

public class EvaluationFragment extends Fragment {

    private RecyclerView recyclerViewEvaluations;
    private Switch toggleSwitch;
    private LinearLayout toggleContainer;
    private Button buttonAddEvaluation;

    // Adapter / controller
    private EvaluationAdapter evaluationAdapter;
    private GradeAdapter gradeAdapter;
    private EvaluationController evaluationController;
    private GradeController gradeController;

    // manager
    private EvaluationManager evaluationManager;
    private GradeManager gradeManager;
    private StudentManager studentManager;



    private int parentId; // ID du parent (e.g., Course ou CompositeEvaluation)
    private String parentType; // Type du parent ("Course" ou "CompositeEvaluation")
    private String parentName;
    private Bloc selectedBloc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        recyclerViewEvaluations = view.findViewById(R.id.recyclerViewEvaluations);
        toggleSwitch = view.findViewById(R.id.toggleSwitch);
        LinearLayout toggleContainer = view.findViewById(R.id.toggleContainer);
        buttonAddEvaluation = view.findViewById(R.id.addEvaluationButton);

        recyclerViewEvaluations.setLayoutManager(new LinearLayoutManager(requireContext()));
        evaluationAdapter = new EvaluationAdapter(List.of(), evaluation -> {
            if ("Composite".equals(evaluation.getType())) {
                navigateToSubEvaluations(evaluation);
            } else if ("Final".equals(evaluation.getType())) {
                navigateToGrades(evaluation);
            }
        });
        recyclerViewEvaluations.setAdapter(evaluationAdapter);
        gradeAdapter = new GradeAdapter(List.of(), requireActivity(),grade -> navigateToGradeDetail(grade));

        evaluationManager = new EvaluationManager(AppDatabase.getInstance(requireContext()));
        gradeManager = new GradeManager(AppDatabase.getInstance(requireContext()));
        studentManager = new StudentManager(AppDatabase.getInstance(requireContext()));


        // Récupérer les arguments
        if (getArguments() != null) {
            parentId = getArguments().getInt("parentId");
            parentType = getArguments().getString("parentType");
            selectedBloc = (Bloc) getArguments().getSerializable("selectedBloc");
        }

        evaluationController = new EvaluationController(evaluationManager, requireActivity(), evaluationAdapter, parentId, parentType);
        gradeController = new GradeController(gradeManager, evaluationManager, studentManager, requireActivity(), gradeAdapter,parentId , selectedBloc,parentId);
        // Afficher ou masquer le toggle selon le type
        if ("CompositeEvaluation".equals(parentType)) {
            toggleContainer.setVisibility(View.VISIBLE);
        } else {
            toggleContainer.setVisibility(View.GONE);
        }

        // Gérer les changements de toggle
        toggleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                gradeController.loadCompositeGrades();
                recyclerViewEvaluations.setAdapter(gradeAdapter);
                buttonAddEvaluation.setVisibility(View.GONE);
            } else {
                evaluationController.loadEvaluations();
                recyclerViewEvaluations.setAdapter(evaluationAdapter);
                buttonAddEvaluation.setVisibility(View.VISIBLE);
            }
        });

        // Bouton Ajouter
        buttonAddEvaluation.setOnClickListener(this::showAddEvaluationDialog);

        evaluationController.setParentName(view);
        evaluationController.loadEvaluations(); // Par défaut, charger les sous-évaluations
        return view;
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

                evaluationController.addEvaluation(maxPoints, evaluationName, type);
            }
        });

        cancelButton.setOnClickListener(view -> popupWindow.dismiss());
    }

    private void navigateToSubEvaluations(Evaluation compositeEvaluation) {
        // Naviguer vers un nouveau fragment pour afficher les sous-évaluations
        Bundle args = new Bundle();
        args.putInt("parentId", compositeEvaluation.getId());
        args.putString("parentType", "CompositeEvaluation");
        args.putSerializable("selectedBloc", selectedBloc);
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToGrades(Evaluation finalEvaluation) {
        // Naviguer vers le fragment GradeFragment pour afficher les notes
        Bundle args = new Bundle();
        args.putInt("evaluationId", finalEvaluation.getId());
        args.putSerializable("selectedBloc", selectedBloc);
        GradeFragment fragment = new GradeFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToGradeDetail(Grade grade) {
        Bundle args = new Bundle();
        args.putInt("gradeId", grade.getId());
        GradeDetailFragment fragment = new GradeDetailFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
