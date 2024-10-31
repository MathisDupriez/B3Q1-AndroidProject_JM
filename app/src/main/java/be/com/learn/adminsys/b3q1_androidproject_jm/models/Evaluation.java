package be.com.learn.adminsys.b3q1_androidproject_jm.models;

import java.util.ArrayList;
import java.util.List;

class Evaluation {
    private String nom;
    private ArrayList<Grade> grades;

    public Evaluation(String nom) {
        this.nom = nom;
        this.grades = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }
}
