package be.com.learn.adminsys.b3q1_androidproject_jm.models;

import java.util.List;
import java.util.ArrayList;


public class Course {
    private String nom;
    private String moyEtu;
    private ArrayList<Evaluation> evaluations;
    private Bloc bloc;

    public Course(String nom) {
        this.nom = nom;
        this.evaluations = new ArrayList<>();
    }

    public Course(String c3, String chime, Bloc bloc2, ArrayList<Object> objects) {
        this.nom = nom;
        this.evaluations = new ArrayList<>();
        this.bloc = bloc;

    }

    public String getNom() {
        return nom;
    }

    public void addEvaluation(Evaluation eval) {
        evaluations.add(eval);
    }

    public Object getBloc() {
        return bloc;
    }

    public String getCourseName() {
        return nom;

    }
}