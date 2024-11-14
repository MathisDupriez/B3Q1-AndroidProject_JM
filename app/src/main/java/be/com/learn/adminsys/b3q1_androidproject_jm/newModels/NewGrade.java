package be.com.learn.adminsys.b3q1_androidproject_jm.newModels;

public class NewGrade {
    private NewStudent student;
    private NewEvaluation evaluation;
    private double note;

    public NewGrade(NewStudent student, NewEvaluation evaluation, double note) {
        this.student = student;
        this.evaluation = evaluation;
        this.note = note;
    }

    // Getters et Setters
    public NewStudent getStudent() {
        return student;
    }

    public void setStudent(NewStudent student) {
        this.student = student;
    }

    public NewEvaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(NewEvaluation evaluation) {
        this.evaluation = evaluation;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public double getPercentage() {
        return (note / evaluation.getMaxPoints()) * 100;
    }

    @Override
    public String toString() {
        return "Étudiant : " + student + ", Note : " + note + "/" + evaluation.getMaxPoints();
    }
}
