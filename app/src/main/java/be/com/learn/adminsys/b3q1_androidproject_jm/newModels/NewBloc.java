package be.com.learn.adminsys.b3q1_androidproject_jm.newModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewBloc implements Serializable {
    private String name;
    private Map<String, NewStudent> students;       // Map d'étudiants, avec le matricule comme clé
    private List<NewEvaluation> evaluations;        // Liste d'évaluations gérée par le bloc

    // Constructor
    public NewBloc(String name) {
        this.name = name;
        this.students = new HashMap<>();    // Initialisation de la Map des étudiants
        this.evaluations = new ArrayList<>();
    }

    // Getters et Setters pour les attributs de base
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Méthodes de gestion des étudiants
    public void addStudent(NewStudent student) {
        // Vérifie si le matricule est déjà dans la Map
        if (students.containsKey(student.getMatricule())) {
            throw new IllegalArgumentException("Erreur : Un étudiant avec le matricule " + student.getMatricule() + " existe déjà.");
        } else {
            students.put(student.getMatricule(), student);
        }
    }

    public void removeStudent(String matricule) {
        students.remove(matricule);
    }

    public NewStudent getStudentByMatricule(String matricule) {
        return students.get(matricule);
    }

    public List<NewStudent> getStudents() {
        return new ArrayList<>(students.values());  // Retourne une copie des étudiants sous forme de liste
    }

    public int getStudentCount() {
        return students.size();
    }

    public boolean hasStudent(String matricule) {
        return students.containsKey(matricule);
    }

    // Méthodes de gestion des évaluations
    public void addEvaluation(NewEvaluation evaluation) {
        if (!evaluations.contains(evaluation)) {  // Évite les doublons
            evaluations.add(evaluation);
        }
    }

    public void removeEvaluation(NewEvaluation evaluation) {
        evaluations.remove(evaluation);
    }

    public List<NewEvaluation> getEvaluations() {
        return new ArrayList<>(evaluations);  // Retourne une copie pour éviter les modifications directes
    }

    public int getEvaluationCount() {
        return evaluations.size();
    }

    public boolean hasEvaluation(NewEvaluation evaluation) {
        return evaluations.contains(evaluation);
    }

    @Override
    public String toString() {
        return name;
    }
}
