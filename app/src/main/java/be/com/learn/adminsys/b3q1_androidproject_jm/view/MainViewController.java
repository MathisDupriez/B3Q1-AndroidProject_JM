package be.com.learn.adminsys.b3q1_androidproject_jm.view;

import java.util.ArrayList;
import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.models.Course;

public class MainViewController {

    private List<Bloc> blocs = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    // Initialiser des données fictives
    public MainViewController() {
        initializeDummyData(); // Initialisation des données
    }

    private void initializeDummyData() {
        // Créer des blocs
        Bloc bloc1 = new Bloc("B1", "Bloc 1", new ArrayList<>(), new ArrayList<>(), null);
        Bloc bloc2 = new Bloc("B2", "Bloc 2", new ArrayList<>(), new ArrayList<>(), null);
        Bloc bloc3 = new Bloc("B3", "Bloc 3", new ArrayList<>(), new ArrayList<>(), null);
        Bloc bloc4 = new Bloc("B4", "Bloc 4", new ArrayList<>(), new ArrayList<>(), null);

        blocs.add(bloc1);
        blocs.add(bloc2);
        blocs.add(bloc3);
        blocs.add(bloc4);

        // Créer des cours
        courses.add(new Course("C1", "Mathématiques", bloc1, new ArrayList<>()));
        courses.add(new Course("C2", "Physique", bloc1, new ArrayList<>()));
        courses.add(new Course("C3", "Chime", bloc2, new ArrayList<>()));
    }

    // Méthode pour récupérer les blocs
    public List<Bloc> getBlocs() {
        return blocs;
    }

    // Méthode pour récupérer les cours associés à un bloc
    public List<Course> getCoursesForBloc(Bloc bloc) {
        List<Course> blocCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getBloc().equals(bloc)) {
                blocCourses.add(course);
            }
        }
        return blocCourses;
    }

    public List<String> getBlocNames() {
        List<String> blocNames = new ArrayList<>();
        for (Bloc bloc : blocs) {
            blocNames.add(bloc.getName());
        }
        return blocNames;
    }
}
