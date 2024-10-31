package be.com.learn.adminsys.b3q1_androidproject_jm.models;

import java.util.List;

public class Evaluation {
    private String evaluationId;
    private String evaluationName;
    private Course course; // Le cours auquel cette évaluation est associée
    private List<Grade> grades; // Liste des notes pour cette évaluation

    // Constructeur
    public Evaluation(String evaluationId, String evaluationName, Course course, List<Grade> grades) {
        this.evaluationId = evaluationId;
        this.evaluationName = evaluationName;
        this.course = course;
        this.grades = grades;
    }

    // Getters et Setters
    public String getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(String evaluationId) {
        this.evaluationId = evaluationId;
    }

    public String getEvaluationName() {
        return evaluationName;
    }

    public void setEvaluationName(String evaluationName) {
        this.evaluationName = evaluationName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "evaluationId='" + evaluationId + '\'' +
                ", evaluationName='" + evaluationName + '\'' +
                ", course=" + (course != null ? course.getCourseName() : "null") +
                ", notes=" + grades +
                '}';
    }
}
