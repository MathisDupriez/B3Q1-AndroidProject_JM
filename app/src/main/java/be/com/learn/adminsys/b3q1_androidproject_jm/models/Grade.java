package be.com.learn.adminsys.b3q1_androidproject_jm.models;

public class Grade {
    private String gradeId;
    private Student student; // Étudiant auquel la note est attribuée
    private Evaluation evaluation; // Évaluation à laquelle la note est liée
    private double gradeValue; // La valeur de la note

    // Constructeur
    public Grade(String gradeId, Student student, Evaluation evaluation, double gradeValue) {
        this.gradeId = gradeId;
        this.student = student;
        this.evaluation = evaluation;
        this.gradeValue = gradeValue;
    }

    // Getters et Setters
    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(double gradeValue) {
        this.gradeValue = gradeValue;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId='" + gradeId + '\'' +
                ", student=" + (student != null ? student.getFirstName() + " " + student.getLastName() : "null") +
                ", evaluation=" + (evaluation != null ? evaluation.getEvaluationName() : "null") +
                ", gradeValue=" + gradeValue +
                '}';
    }
}
