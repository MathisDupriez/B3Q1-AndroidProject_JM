package be.com.learn.adminsys.b3q1_androidproject_jm.newModels;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class NewEvaluation implements Serializable {
    private String evaluationId;
    private String title;
    private String description;
    private int maxPoints;
    private Map<NewStudent, NewGrade> grades;
    private List<NewEvaluation> subEvaluations;
    private Map<String, NewStudent> students;  // Map des étudiants, initialisée dans le constructeur

    // Constructor
    public NewEvaluation(String evaluationId, String title, String description, int maxPoints, Map<String, NewStudent> students) {
        this.evaluationId = evaluationId;
        this.title = title;
        this.description = description;
        this.maxPoints = maxPoints;
        this.grades = new HashMap<>();
        this.subEvaluations = new ArrayList<>();
        this.students = Collections.unmodifiableMap(new HashMap<>(students));  // Rendre la Map des étudiants immuable
    }

    // Getters et Setters pour les autres attributs
    public String getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(String evaluationId) {
        this.evaluationId = evaluationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Map<NewStudent, NewGrade> getGrades() {
        return grades;
    }

    public List<NewEvaluation> getSubEvaluations() {
        return subEvaluations;
    }

    // Méthode pour ajouter une note
    public void addGradeForStudent(NewStudent student, double note) {
        if (students.containsKey(student.getMatricule())) {  // Vérifie que l'étudiant est assigné à l'évaluation
            NewGrade grade = new NewGrade(student, this, note);
            grades.put(student, grade);
        } else {
            throw new IllegalArgumentException("L'étudiant " + student.getMatricule() + " n'est pas assigné à cette évaluation.");
        }
    }

    // Méthode pour ajouter une sous-évaluation
    public void addSubEvaluation(NewEvaluation subEvaluation) {
        subEvaluations.add(subEvaluation);
    }

    // Getter pour la Map des étudiants assignés
    public Map<String, NewStudent> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return title;
    }
}
