package be.com.learn.adminsys.b3q1_androidproject_jm.models;

import java.util.List;

public class Bloc {
    // Attributs
    private String mName;          // Nom du bloc
    private List<String> mListStudent; // Liste des étudiants dans ce bloc
    private List<String> mListBloc;    // Liste des sous-blocs (si applicable)
    private int mIdParent;             // ID du bloc parent
    private int mId;                   // ID unique de ce bloc

    // Constructeur par défaut
    public Bloc() {
        // Utilisé pour l'initialisation sans paramètres
    }

    // Constructeur pour initialiser les valeurs
    public Bloc(int id, String name, List<String> listStudent, List<String> listBloc, int idParent) {
        this.mId = id;
        this.mName = name;
        this.mListStudent = listStudent;
        this.mListBloc = listBloc;
        this.mIdParent = idParent;
    }

    // Getters et Setters
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public List<String> getListStudent() {
        return mListStudent;
    }

    public void setListStudent(List<String> listStudent) {
        this.mListStudent = listStudent;
    }

    public List<String> getListBloc() {
        return mListBloc;
    }

    public void setListBloc(List<String> listBloc) {
        this.mListBloc = listBloc;
    }

    public int getIdParent() {
        return mIdParent;
    }

    public void setIdParent(int idParent) {
        this.mIdParent = idParent;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    // Méthode toString pour afficher le contenu du bloc
    @Override
    public String toString() {
        return "Bloc{" +
                "id=" + mId +
                ", name='" + mName + '\'' +
                ", listStudent=" + mListStudent +
                ", listBloc=" + mListBloc +
                ", idParent=" + mIdParent +
                '}';
    }
}
