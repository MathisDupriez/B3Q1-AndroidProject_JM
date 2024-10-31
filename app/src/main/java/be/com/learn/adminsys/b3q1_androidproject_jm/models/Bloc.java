package be.com.learn.adminsys.b3q1_androidproject_jm.models;

import java.util.List;

public class Bloc {
    private String blocId;
    private String name;
    private List<Student> students;  // Liste des étudiants (référence directe)
    private List<Bloc> subBlocs;     // Liste des sous-blocs
    private Bloc parentBloc;         // Référence au bloc parent

    // Constructor
    public Bloc(String blocId, String name, List<Student> students, List<Bloc> subBlocs, Bloc parentBloc) {
        this.blocId = blocId;
        this.name = name;
        this.students = students;
        this.subBlocs = subBlocs;
        this.parentBloc = parentBloc;
    }

    // Getters et Setters
    public String getBlocId() {
        return blocId;
    }

    public void setBlocId(String blocId) {
        this.blocId = blocId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Bloc> getSubBlocs() {
        return subBlocs;
    }

    public void setSubBlocs(List<Bloc> subBlocs) {
        this.subBlocs = subBlocs;
    }

    public Bloc getParentBloc() {
        return parentBloc;
    }

    public void setParentBloc(Bloc parentBloc) {
        this.parentBloc = parentBloc;
    }

    @Override
    public String toString() {
        return "Bloc{" +
                "blocId='" + blocId + '\'' +
                ", name='" + name + '\'' +
                ", students=" + students +
                ", subBlocs=" + subBlocs +
                ", parentBloc=" + (parentBloc != null ? parentBloc.getName() : "null") +
                '}';
    }
}
