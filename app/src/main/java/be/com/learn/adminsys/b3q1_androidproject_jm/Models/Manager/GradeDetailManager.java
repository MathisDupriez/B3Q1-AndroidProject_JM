package be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;

public class GradeDetailManager {

    private AppDatabase db;

    public GradeDetailManager(AppDatabase db) {
        this.db = db;
    }

    // Récupérer un grade par son ID
    public Grade getGradeById(int gradeId) {
        return db.gradeDao().getGradeById(gradeId);
    }

    // Récupérer l'étudiant associé à un grade
    public Student getStudentByGrade(Grade grade) {
        return db.studentDao().getStudentById(grade.getStudentId());
    }

    // Récupérer l'évaluation associée à un grade
    public Evaluation getEvaluationByGrade(Grade grade) {
        return db.evaluationDao().getEvaluationById(grade.getEvaluationId());
    }

    // Mettre à jour un grade
    public void updateGrade(Grade grade) {
        db.gradeDao().update(grade);
    }
}
