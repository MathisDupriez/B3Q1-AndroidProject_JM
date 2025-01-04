package be.com.learn.adminsys.b3q1_androidproject_jm.Models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.NewEvaluation;

public class Course implements Serializable, EvaluationParent {
    private Map<String, Student> students;
    private List<NewEvaluation> evaluations;
    private String name;

    public Course(String name, Map<String, Student> students, List<NewEvaluation> evaluations) {
        this.name = name;
        this.students = students;
        this.evaluations = evaluations;
    }
    // Getters et Setters
    // Get the name of the course
    public String getName() {
        return name;
    }
    // get the list of students in the course
    public Map<String, Student> getStudents() {
        return students;
    }
    // get the list of evaluations in the course
    @Override
    public List<NewEvaluation> getEvaluations() {
        return evaluations;
    }
    // add evaluation to the course
    @Override
    public void addEvaluation(NewEvaluation evaluation) {
        evaluations.add(evaluation);
    }
}
