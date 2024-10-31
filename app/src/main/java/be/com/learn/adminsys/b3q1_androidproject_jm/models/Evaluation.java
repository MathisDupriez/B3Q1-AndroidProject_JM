package be.com.learn.adminsys.b3q1_androidproject_jm.models;

import java.util.List;

public class Evaluation extends Course {
    private int mMaxPoint; // Maximum de points pour l'évaluation

    // Constructeur par défaut
    public Evaluation() {
        super(); // Appelle le constructeur par défaut de Course
    }

    // Constructeur avec paramètres
    public Evaluation(int id, String name, List<String> listStudent, List<String> listBloc, int idParent, double studentMoyen, int maxPoint) {
        super(id, name, listStudent, listBloc, idParent, studentMoyen); // Appelle le constructeur de Course
        this.mMaxPoint = maxPoint;
    }

    // Getter et Setter pour maxPoint
    public int getmMaxPoint() {
        return mMaxPoint;
    }

    public void setmMaxPoint(int mMaxPoint) {
        this.mMaxPoint = mMaxPoint;
    }

    // Redéfinition de toString pour inclure le maximum de points
    @Override
    public String toString() {
        return "Evaluation{" +
                "maxPoint=" + mMaxPoint +
                ", " + super.toString() + // Appel à toString() de Course pour inclure les informations du parent
                '}';
    }
}
