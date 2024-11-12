package be.com.learn.adminsys.b3q1_androidproject_jm.oldModels;

import java.util.List;

public class Evaluation extends Course {

    // Constructeur principal
    public Evaluation(String blocId, String name, List<Student> students, List<Bloc> subBlocs, Bloc parentBloc) {
        super(blocId, name, students, subBlocs, parentBloc);
    }

    // Constructeur sans paramètres
    public Evaluation() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + " - Evaluation";
    }
}
