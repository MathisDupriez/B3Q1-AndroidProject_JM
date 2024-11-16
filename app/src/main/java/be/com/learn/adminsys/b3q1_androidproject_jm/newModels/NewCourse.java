package be.com.learn.adminsys.b3q1_androidproject_jm.newModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.com.learn.adminsys.b3q1_androidproject_jm.newModels.evaluation.NewEvaluation;

public class NewCourse implements Serializable {
    private Map<String,NewStudent> students;
    private List<NewEvaluation> evaluations;
    private String name;

    public NewCourse(String name,Map<String,NewStudent> students, List<NewEvaluation> evaluations) {
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
    public Map<String, NewStudent> getStudents() {
        return students;
    }
    // get the list of evaluations in the course
    public List<NewEvaluation> getEvaluations() {
        return evaluations;
    }
    // add evaluation to the course
    public void addEvaluation(NewEvaluation evaluation) {
        evaluations.add(evaluation);
    }
}
