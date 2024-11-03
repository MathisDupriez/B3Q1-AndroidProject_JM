package be.com.learn.adminsys.b3q1_androidproject_jm.models;

import java.util.List;
import java.util.ArrayList;

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
