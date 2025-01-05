package be.com.learn.adminsys.b3q1_androidproject_jm.Fragments;

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
import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.GradeAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class EvaluationFragment extends Fragment {

    private RecyclerView recyclerViewEvaluations;
    private EvaluationAdapter evaluationAdapter;
    private GradeAdapter gradeAdapter;
    private AppDatabase db;
    private int parentId; // ID du parent (e.g., Course ou CompositeEvaluation)
    private String parentType; // Type du parent ("Course" ou "CompositeEvaluation")
    private String parentName;
    private Bloc selectedBloc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        recyclerViewEvaluations = view.findViewById(R.id.recyclerViewEvaluations);
        recyclerViewEvaluations.setLayoutManager(new LinearLayoutManager(requireContext()));

        db = AppDatabase.getInstance(requireContext());

        // Toggle et son conteneur
        Switch toggleSwitch = view.findViewById(R.id.toggleSwitch);
        LinearLayout toggleContainer = view.findViewById(R.id.toggleContainer);

        // Récupérer les arguments
        if (getArguments() != null) {
            parentId = getArguments().getInt("parentId");
            parentType = getArguments().getString("parentType");
            selectedBloc = (Bloc) getArguments().getSerializable("selectedBloc");
        }

        // Afficher ou masquer le toggle selon le type
        if ("CompositeEvaluation".equals(parentType)) {
            toggleContainer.setVisibility(View.VISIBLE);
        } else {
            toggleContainer.setVisibility(View.GONE);
        }

        // Gérer les changements de toggle
        toggleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                loadGrades();
            } else {
                loadEvaluations();
            }
        });

        // Bouton Ajouter
        Button buttonAddEvaluation = view.findViewById(R.id.addEvaluationButton);
        buttonAddEvaluation.setOnClickListener(this::showAddEvaluationDialog);

        setParentName();
        loadEvaluations(); // Par défaut, charger les sous-évaluations
        return view;
    }

    private void setParentName() {
        Executors.newSingleThreadExecutor().execute(() -> {
            if ("Course".equals(parentType)) {
                parentName = "Evaluation du cours : " + db.courseDao().getCourseById(parentId).getName();
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
                    } else if ("Final".equals(evaluation.getType())) {
                        navigateToGrades(evaluation);
                    }
                });
                recyclerViewEvaluations.setAdapter(evaluationAdapter);
            });
        });
    }

    private void loadGrades() {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Récupère les grades pour l'évaluation
            List<Grade> grades = db.gradeDao().getGradesByEvaluationId(parentId);

            // Récupère tous les étudiants du bloc
            List<Student> blocStudents = db.studentDao().getStudentsByBlocId(selectedBloc.getId());
            boolean isGradeAdded = false;

            // Vérifie si chaque étudiant du bloc a un grade pour l'évaluation
            for (Student student : blocStudents) {
                boolean hasGrade = false;
                for (Grade grade : grades) {
                    if (grade.getStudentId() == student.getId()) {
                        hasGrade = true;
                        break;
                    }
                }
                // Si l'étudiant n'a pas encore de grade, en crée un avec une note par défaut (e.g., 0)
                if (!hasGrade) {
                    Grade newGrade = new Grade(0, student.getId(), parentId);
                    db.gradeDao().insert(newGrade); // Insère le nouveau grade dans la base
                    grades.add(newGrade); // Ajoute le grade à la liste locale
                    isGradeAdded = true;
                }
            }

            // Si des grades ont été ajoutés, recharge les données
            if (isGradeAdded) {
                loadGrades();
                return;
            }

            // Met à jour l'interface utilisateur dans le thread principal
            requireActivity().runOnUiThread(() -> {
                gradeAdapter = new GradeAdapter(grades, db, grade -> {
                    // Action lors du clic sur un élément
                    navigateToGradeDetail(grade);
                });
                recyclerViewEvaluations.setAdapter(gradeAdapter);
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
                    long evalID = db.evaluationDao().insert(newEvaluation);



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
