package be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs.EvaluationDao;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;

public class EvaluationManager {

    private EvaluationDao evaluationDao;

    public EvaluationManager(AppDatabase db) {
        this.evaluationDao = db.evaluationDao();
    }

    public List<Evaluation> getEvaluationsByParent(int parentId, String parentType) {
        return evaluationDao.getEvaluationsByParent(parentId, parentType);
    }

    public void addEvaluation(Evaluation evaluation) {
        evaluationDao.insert(evaluation);
    }

    public Evaluation getEvaluationById(int evaluationId) {
        return evaluationDao.getEvaluationById(evaluationId);
    }
    public List<Evaluation> getEvaluationsByParentId(int parentId){
        return evaluationDao.getEvaluationsByParentId(parentId);
    }
}
