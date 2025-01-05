package be.com.learn.adminsys.b3q1_androidproject_jm.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.GradeAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class GradeFragment extends Fragment {

    private RecyclerView recyclerViewGrades;
    private GradeAdapter gradeAdapter;
    private AppDatabase db;
    private int evaluationId; // ID de l'évaluation associée
    private String evaluationName;

    private Bloc selectedBloc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grade, container, false);
        recyclerViewGrades = view.findViewById(R.id.recyclerViewGrades);
        recyclerViewGrades.setLayoutManager(new LinearLayoutManager(requireContext()));

        db = AppDatabase.getInstance(requireContext());

        // Récupérer les arguments
        if (getArguments() != null) {
            evaluationId = getArguments().getInt("evaluationId");
            selectedBloc = (Bloc) getArguments().getSerializable("selectedBloc");
        }

        // Mettre à jour le titre
        setEvaluationName();
        loadGrades();
        return view;
    }

    private void setEvaluationName() {
        Executors.newSingleThreadExecutor().execute(() -> {
            evaluationName = db.evaluationDao().getEvaluationById(evaluationId).getName();

            requireActivity().runOnUiThread(() -> {
                TextView textGradeTitle = requireView().findViewById(R.id.textGradeTitle);
                textGradeTitle.setText("Notes pour l'évaluation : " + evaluationName);
            });
        });
    }

    private void loadGrades() {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Récupère les grades pour l'évaluation
            List<Grade> grades = db.gradeDao().getGradesByEvaluationId(evaluationId);

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
                    Grade newGrade = new Grade(0, student.getId(), evaluationId);
                    db.gradeDao().insert(newGrade); // Insère le nouveau grade dans la base
                    grades.add(newGrade); // Ajoute le grade à la liste locale
                    isGradeAdded = true;
                }
            }
            if (isGradeAdded){
                loadGrades();
                return;
            }

            // Met à jour l'interface utilisateur dans le thread principal
            requireActivity().runOnUiThread(() -> {
                gradeAdapter = new GradeAdapter(grades,db ,grade -> {
                    // Action lors du clic sur un élément
                    navigateToGradeDetail(grade.getId());
                });
                recyclerViewGrades.setAdapter(gradeAdapter);
            });
        });
    }



    private void navigateToGradeDetail(int gradeId) {
        Bundle args = new Bundle();
        args.putInt("gradeId", gradeId);

        GradeDetailFragment fragment = new GradeDetailFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
