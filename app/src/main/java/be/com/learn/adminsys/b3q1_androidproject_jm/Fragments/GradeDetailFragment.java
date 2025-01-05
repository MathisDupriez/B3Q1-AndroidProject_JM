package be.com.learn.adminsys.b3q1_androidproject_jm.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.Executors;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class GradeDetailFragment extends Fragment {

    private AppDatabase db;
    private int gradeId; // ID du grade pour récupérer les détails

    private TextView textViewGradeTitle;
    private TextView textViewStudentLastName;
    private TextView textViewStudentFirstName;
    private TextView textViewStudentMatricule;
    private EditText editTextGradePoint; // Permet de modifier la note
    private TextView textViewMaxPoints;
    private Button buttonSaveGrade;

    private Button buttonBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grade_detail, container, false);

        // Initialiser les vues
        textViewGradeTitle = view.findViewById(R.id.textViewGradeTitle);
        textViewStudentLastName = view.findViewById(R.id.textViewStudentLastName);
        textViewStudentFirstName = view.findViewById(R.id.textViewStudentFirstName);
        textViewStudentMatricule = view.findViewById(R.id.textViewStudentMatricule);
        editTextGradePoint = view.findViewById(R.id.editTextGradePoint);
        textViewMaxPoints = view.findViewById(R.id.textViewMaxPoints);
        buttonSaveGrade = view.findViewById(R.id.buttonSaveGrade);
        buttonBack = view.findViewById(R.id.buttonBack);

        db = AppDatabase.getInstance(requireContext());

        // Récupérer les arguments
        if (getArguments() != null) {
            gradeId = getArguments().getInt("gradeId");
        }

        loadGradeDetails();

        buttonSaveGrade.setOnClickListener(v -> saveGrade());

        buttonBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void loadGradeDetails() {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Récupérer les détails du grade
            Grade grade = db.gradeDao().getGradeById(gradeId);

            if (grade != null) {
                // Récupérer l'étudiant et l'évaluation associés
                Student student = db.studentDao().getStudentById(grade.getStudentId());
                Evaluation evaluation = db.evaluationDao().getEvaluationById(grade.getEvaluationId());

                // Mettre à jour l'interface utilisateur dans le thread principal
                requireActivity().runOnUiThread(() -> {
                    if (student != null) {
                        textViewStudentLastName.setText(student.getLastName());
                        textViewStudentFirstName.setText(student.getFirstName());
                        textViewStudentMatricule.setText(student.getMatricule());
                    } else {
                        textViewStudentLastName.setText("Inconnu");
                        textViewStudentFirstName.setText("Inconnu");
                        textViewStudentMatricule.setText("Inconnu");
                    }

                    editTextGradePoint.setText(String.valueOf(grade.getPoint()));

                    if (evaluation != null) {
                        textViewGradeTitle.setText("Note à : " + evaluation.getName());
                        textViewMaxPoints.setText(String.valueOf(evaluation.getMaxPoints()));
                    } else {
                        textViewGradeTitle.setText("Note de : Inconnu");
                        textViewMaxPoints.setText("Inconnu");
                    }
                });
            }
        });
    }

    private void saveGrade() {
        String gradePointStr = editTextGradePoint.getText().toString();

        if (gradePointStr.isEmpty()) {
            Toast.makeText(requireContext(), "La note ne peut pas être vide", Toast.LENGTH_SHORT).show();
            return;
        }

        double gradePoint = Double.parseDouble(gradePointStr);

        Executors.newSingleThreadExecutor().execute(() -> {
            Grade grade = db.gradeDao().getGradeById(gradeId);

            if (grade != null) {
                Evaluation evaluation = db.evaluationDao().getEvaluationById(grade.getEvaluationId());
                if (evaluation != null) {
                    double maxPoints = evaluation.getMaxPoints();

                    // Vérifier les contraintes sur la note
                    if (gradePoint < 0) {
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(requireContext(), "La note ne peut pas être inférieure à 0", Toast.LENGTH_SHORT).show());
                        return;
                    }

                    if (gradePoint > maxPoints) {
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(requireContext(), "La note ne peut pas dépasser " + maxPoints, Toast.LENGTH_SHORT).show());
                        return;
                    }

                    // Mettre à jour la note si elle est valide
                    grade.setPoint(gradePoint);
                    db.gradeDao().update(grade);

                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(requireContext(), "Note mise à jour", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    });
                } else {
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(requireContext(), "Évaluation introuvable", Toast.LENGTH_SHORT).show());
                }
            } else {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Grade introuvable", Toast.LENGTH_SHORT).show());
            }
        });
    }
}

