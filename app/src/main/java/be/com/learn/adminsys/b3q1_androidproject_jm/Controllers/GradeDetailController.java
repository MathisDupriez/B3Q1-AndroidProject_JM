package be.com.learn.adminsys.b3q1_androidproject_jm.Controllers;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.GradeDetailManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Fragment.GradeDetailFragment;

import java.util.concurrent.Executors;

public class GradeDetailController {

    private GradeDetailManager gradeDetailManager;
    private GradeDetailFragment fragment;

    public GradeDetailController(GradeDetailFragment fragment) {
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            this.gradeDetailManager = new GradeDetailManager(AppDatabase.getInstance(activity));
        }
        this.fragment = fragment;
    }

    public void loadGradeDetails(int gradeId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Récupérer les détails du grade
            Grade grade = gradeDetailManager.getGradeById(gradeId);

            if (grade != null) {
                // Récupérer l'étudiant et l'évaluation associés
                Student student = gradeDetailManager.getStudentByGrade(grade);
                Evaluation evaluation = gradeDetailManager.getEvaluationByGrade(grade);

                // Passer les données à la vue (Fragment)
                if (student != null && evaluation != null) {
                    String gradePoint = String.valueOf(grade.getDisplayPoint());
                    String gradeTitle = "Note à : " + evaluation.getName();
                    String maxPoints = String.valueOf(evaluation.getMaxPoints());
                    boolean isFinalEvaluation = "Final".equals(evaluation.getType());
                    boolean isForced = grade.isForced();

                    // Appel à la méthode du fragment pour mettre à jour la vue sur le thread principal
                    fragment.getActivity().runOnUiThread(() -> {
                        fragment.updateGradeDetails(student.getLastName(), student.getFirstName(),
                                student.getMatricule(), gradePoint, gradeTitle, maxPoints, isFinalEvaluation, isForced);
                    });
                }
            }
        });
    }

    public void saveGrade(int gradeId, String gradePointStr, boolean isForced) {
        if (gradePointStr.isEmpty()) {
            fragment.getActivity().runOnUiThread(() -> fragment.showError("La note ne peut pas être vide"));
            return;
        }

        double gradePoint = Double.parseDouble(gradePointStr);

        Executors.newSingleThreadExecutor().execute(() -> {
            Grade grade = gradeDetailManager.getGradeById(gradeId);

            if (grade != null) {
                Evaluation evaluation = gradeDetailManager.getEvaluationByGrade(grade);
                if (evaluation != null) {
                    double maxPoints = evaluation.getMaxPoints();

                    if (gradePoint < 0) {
                        fragment.getActivity().runOnUiThread(() -> fragment.showError("La note ne peut pas être inférieure à 0"));
                        return;
                    }

                    if (gradePoint > maxPoints) {
                        fragment.getActivity().runOnUiThread(() -> fragment.showError("La note ne peut pas dépasser " + maxPoints));
                        return;
                    }

                    grade.setPoint(gradePoint);
                    grade.setForced(isForced);

                    gradeDetailManager.updateGrade(grade);

                    fragment.getActivity().runOnUiThread(() -> {
                        fragment.showSuccess("Note mise à jour");
                    });
                }
            } else {
                fragment.getActivity().runOnUiThread(() -> fragment.showError("Grade introuvable"));
            }
        });
    }
}
