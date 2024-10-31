package be.com.learn.adminsys.b3q1_androidproject_jm.models;

import java.util.List;
import java.util.ArrayList;

public class Course extends Bloc {
    private double mStudentMoyen; // Moyenne des notes des étudiants
    private List<Evaluation> mListEvaluation; // Liste des évaluations

    // Constructeur par défaut
    public Course() {
        super(); // Appelle le constructeur par défaut de Bloc
        this.mListEvaluation = new ArrayList<>(); // Initialisation de la liste d'évaluations
    }

    // Constructeur avec paramètres
    public Course(int id,
                  String name,
                  List<String> listStudent,
                  List<String> listBloc,
                  int idParent,
                  double studentMoyen) {
        super(id, name, listStudent, listBloc, idParent); // Appelle le constructeur de Bloc
        this.mStudentMoyen = studentMoyen;
        this.mListEvaluation = new ArrayList<>(); // Initialisation de la liste d'évaluations
    }

    // Getter et Setter pour l'attribut mStudentMoyen
    public double getStudentMoyen() {
        return mStudentMoyen;
    }

    public void setStudentMoyen(double studentMoyen) {
        this.mStudentMoyen = studentMoyen;
    }

    // Getter et Setter pour la liste d'évaluations
    public List<Evaluation> getListEvaluation() {
        return mListEvaluation;
    }

    public void setListEvaluation(List<Evaluation> listEvaluation) {
        this.mListEvaluation = listEvaluation;
    }

    // Redéfinition de toString pour inclure la moyenne des étudiants et la liste des évaluations
    @Override
    public String toString() {
        return "Course{" +
                "mStudentMoyen=" + mStudentMoyen +
                ", mListEvaluation=" + mListEvaluation +
                ", " + super.toString() + // Appel à toString() de Bloc pour inclure les informations du parent
                '}';
    }
}
