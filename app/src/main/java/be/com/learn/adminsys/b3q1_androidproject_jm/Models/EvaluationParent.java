package be.com.learn.adminsys.b3q1_androidproject_jm.Models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.NewEvaluation;

public interface EvaluationParent extends Serializable {
    List<NewEvaluation> getEvaluations();
    void addEvaluation(NewEvaluation evaluation);
    Map<String, NewStudent> getStudents();
    String getName();
}

