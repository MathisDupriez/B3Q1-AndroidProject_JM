package be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.EvaluationParent;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;

public class CompositeEvaluation extends NewEvaluation implements Serializable, EvaluationParent {
    private List<NewEvaluation> evaluations;

    public CompositeEvaluation(String name, int maxPoint, Map<String, Student> students) {
        super(name, maxPoint, students);
        evaluations = new ArrayList<>();
    }
    @Override
    public void addEvaluation(NewEvaluation evaluation) {
        evaluations.add(evaluation);
    }
    @Override
    public List<NewEvaluation> getEvaluations() {
        return evaluations;
    }

    @Override
    public double GetAverage() {
        double weightedSum = 0;
        double totalMaxPoints = 0;

        // Calcul de la somme pondérée des moyennes
        for (NewEvaluation evaluation : evaluations) {
            weightedSum += evaluation.GetAverage();
            totalMaxPoints += evaluation.getMaxPoint();
        }

        // Retourner la moyenne pondérée, ramenée au maxPoint de l'évaluation composite
        return (weightedSum / totalMaxPoints) * getMaxPoint();
    }

}
