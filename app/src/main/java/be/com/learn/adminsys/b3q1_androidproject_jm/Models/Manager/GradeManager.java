package be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs.GradeDao;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;

public class GradeManager {

    private GradeDao gradeDao;

    public GradeManager(AppDatabase db) {
        this.gradeDao = db.gradeDao();
    }

    public List<Grade> getGradesByEvaluationId(int evaluationId) {
        return gradeDao.getGradesByEvaluationId(evaluationId);
    }

    public void insertGrade(Grade grade) {
        gradeDao.insert(grade);
    }

    public void updateGrade(Grade grade) {
        gradeDao.update(grade);
    }
    public Grade getGradeByStudentAndEvaluation(int studentId, int evaluationId){
        return gradeDao.getGradeByStudentAndEvaluation(studentId, evaluationId);
    }
}
