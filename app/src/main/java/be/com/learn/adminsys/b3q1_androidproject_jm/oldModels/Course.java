package be.com.learn.adminsys.b3q1_androidproject_jm.oldModels;

import java.util.List;

public class Course extends Bloc {

    // Constructeur principal
    public Course(String blocId, String name, List<Student> students, List<Bloc> subBlocs, Bloc parentBloc) {
        super(blocId, name, students, subBlocs, parentBloc);
    }

    // Constructeur sans paramètres
    public Course() {
        super(null, null, null, null, null);
    }

    @Override
    public String toString() {
        return super.toString() + " - Course";
    }
}
