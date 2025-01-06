package be.com.learn.adminsys.b3q1_androidproject_jm.Controllers;

import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.EvaluationManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.GradeManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.StudentManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Adapter.GradeAdapter;

import java.util.List;
import java.util.concurrent.Executors;

public class GradeController {

    private GradeManager gradeManager;
    private EvaluationManager evaluationManager;
    private StudentManager studentManager;
    private FragmentActivity activity;
    private GradeAdapter gradeAdapter;

    //data needed
    private int evaluationId; // ID de l'évaluation associée
    private Bloc selectedBloc;
    private int parentId;



    public GradeController(GradeManager gradeManager, EvaluationManager Manager,StudentManager studentManager, FragmentActivity activity, GradeAdapter gradeAdapter,int evaluationId,Bloc selectedBloc, int parentId) {
        this.gradeManager = gradeManager;
        this.evaluationManager = Manager;
        this.studentManager = studentManager;
        this.activity = activity;
        this.gradeAdapter = gradeAdapter;
        this.evaluationId = evaluationId;
        this.selectedBloc = selectedBloc;
        this.parentId = parentId;
    }

    public void loadFinalGrades() {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Récupère les grades pour l'évaluation
            List<Grade> grades = gradeManager.getGradesByEvaluationId(evaluationId);

            // Récupère tous les étudiants du bloc
            List<Student> blocStudents = studentManager.getStudentsByBlocId(selectedBloc.getId());
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
                    gradeManager.insertGrade(newGrade); // Ajoute le grade à la base de données
                    grades.add(newGrade); // Ajoute le grade à la liste locale
                    isGradeAdded = true;
                }
            }
            if (isGradeAdded){
                loadFinalGrades();
                return;
            }

            // Met à jour l'interface utilisateur dans le thread principal
            activity.runOnUiThread(() -> gradeAdapter.updateGrades(grades));
        });
    }

    public void loadCompositeGrades() {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Récupère les grades pour l'évaluation
            List<Grade> grades = gradeManager.getGradesByEvaluationId(parentId);

            // Récupère tous les étudiants du bloc
            List<Student> blocStudents = studentManager.getStudentsByBlocId(selectedBloc.getId());
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
                // Si l'étudiant n'a pas encore de grade, on crée un grade par défaut (e.g., 0)
                if (!hasGrade) {
                    Grade newGrade = new Grade(0, student.getId(), parentId);
                    gradeManager.insertGrade(newGrade); // Insère le nouveau grade dans la base
                    grades.add(newGrade); // Ajoute le grade à la liste locale
                    isGradeAdded = true;
                }
            }

            // Si des grades ont été ajoutés, recharge les données
            if (isGradeAdded) {
                loadCompositeGrades();
                return;
            }

            // Processus de calcul de la note composite
            for (Grade grade : grades) {
                // Récupérer l'évaluation associée à la note
                Evaluation evaluation = evaluationManager.getEvaluationById(grade.getEvaluationId());

                if ("Composite".equals(evaluation.getType())) {
                    // Calculer la moyenne pondérée des évaluations enfants
                    double weightedGrade = calculateCompositeGrade(evaluation, grade.getStudentId());

                    // Convertir la note calculée sur 20 en une note sur maxPoints de l'évaluation composite
                    double finalGrade = (weightedGrade / 20) * evaluation.getMaxPoints();

                    // Mise à jour de la note de l'étudiant
                    grade.setPoint(finalGrade);
                    gradeManager.updateGrade(grade); // Mise à jour du grade dans la base
                }
            }

            // Met à jour l'interface utilisateur dans le thread principal
            activity.runOnUiThread(() -> gradeAdapter.updateGrades(grades));
        });
    }

    private double calculateCompositeGrade(Evaluation compositeEvaluation, int studentId) {
        // Vérifier si l'évaluation composite est forcée
        Grade compositeGrade = gradeManager.getGradeByStudentAndEvaluation(studentId, compositeEvaluation.getId());
        if (compositeGrade != null && compositeGrade.isForced()) {
            // Si l'évaluation composite est forcée, retourner la note de cette évaluation
            return compositeGrade.getPoint(); // Prendre la note de l'évaluation forcée
        }

        // Sinon, procéder au calcul de la note composite normalement
        List<Evaluation> childEvaluations = evaluationManager.getEvaluationsByParentId(compositeEvaluation.getId());
        double totalPoints = 0;
        double totalWeight = 0;

        for (Evaluation childEvaluation : childEvaluations) {
            if ("Final".equals(childEvaluation.getType())) {
                // Si l'enfant est une évaluation finale, récupérer la note de l'étudiant
                Grade grade = gradeManager.getGradeByStudentAndEvaluation(studentId, childEvaluation.getId());
                if (grade != null) {
                    // Pondérer la note en fonction du maxPoints de l'évaluation enfant
                    double weight = childEvaluation.getMaxPoints();
                    totalPoints += grade.getPoint();
                    totalWeight += weight;
                }
            } else if ("Composite".equals(childEvaluation.getType())) {
                // Si l'enfant est une évaluation composite, calculer la note récursivement
                double childGrade = calculateCompositeGrade(childEvaluation, studentId);
                double weight = childEvaluation.getMaxPoints();
                totalPoints += childGrade;
                totalWeight += weight;
            }
        }

        // Ramener la note à la base de l'évaluation composite (en fonction de maxPoints)
        if (totalWeight > 0) {
            double compositeGradeFinal = totalPoints / totalWeight; // Note pondérée sur la somme des maxPoints des sous-évaluations

            // Utiliser maxPoints de l'évaluation composite pour ajuster la note
            return compositeGradeFinal * compositeEvaluation.getMaxPoints(); // On ramène la note sur la valeur de l'évaluation composite
        } else {
            return 0;  // Aucun poids, retourner 0
        }
    }
}
