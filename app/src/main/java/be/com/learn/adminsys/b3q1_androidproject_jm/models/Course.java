package be.com.learn.adminsys.b3q1_androidproject_jm.models;

import java.util.List;

public class Course {
    private String courseId;
    private String courseName;
    private Bloc bloc; // Le bloc auquel ce cours est associé
    private List<Evaluation> evaluations; // Les évaluations liées à ce cours

    // Constructeur
    public Course(String courseId, String courseName, Bloc bloc, List<Evaluation> evaluations) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.bloc = bloc;
        this.evaluations = evaluations;
    }

    // Getters et Setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Bloc getBloc() {
        return bloc;
    }

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", bloc=" + (bloc != null ? bloc.getName() : "null") +
                ", evaluations=" + evaluations +
                '}';
    }
}
