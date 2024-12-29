package be.com.learn.adminsys.b3q1_androidproject_jm.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewBloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewCourse;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewStudent;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.CompositeEvaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.FinalEvaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.NewEvaluation;

public class DatabaseCollector {

    public static List<NewBloc> getBlocs() {
        List<NewBloc> blocs = new ArrayList<>();

        // Bloc 1 - Informatique
        List<NewCourse> bloc1Courses = new ArrayList<>();
        Map<String, NewStudent> bloc1Students = createStudents("Alice", "Bob", "Catherine", "David");

        // Ajout de 10 cours sur des sujets informatiques
        bloc1Courses.add(createCourse("Programmation Java", bloc1Students));
        bloc1Courses.add(createCourse("Réseaux informatiques", bloc1Students));
        bloc1Courses.add(createCourse("Bases de données", bloc1Students));
        bloc1Courses.add(createCourse("Systèmes d'exploitation", bloc1Students));
        bloc1Courses.add(createCourse("Algorithmique", bloc1Students));
        bloc1Courses.add(createCourse("Sécurité informatique", bloc1Students));
        bloc1Courses.add(createCourse("Développement mobile", bloc1Students));
        bloc1Courses.add(createCourse("Intelligence artificielle", bloc1Students));
        bloc1Courses.add(createCourse("Cloud computing", bloc1Students));
        bloc1Courses.add(createCourse("DevOps", bloc1Students));

        blocs.add(new NewBloc("Bloc 1", bloc1Students, bloc1Courses));

        // Bloc 2 - Informatique
        List<NewCourse> bloc2Courses = new ArrayList<>();
        Map<String, NewStudent> bloc2Students = createStudents("Eve", "Frank", "George", "Hannah");

        // Ajout de 10 cours similaires pour le Bloc 2
        bloc2Courses.add(createCourse("Programmation Python", bloc2Students));
        bloc2Courses.add(createCourse("Réseaux avancés", bloc2Students));
        bloc2Courses.add(createCourse("Systèmes embarqués", bloc2Students));
        bloc2Courses.add(createCourse("Big Data", bloc2Students));
        bloc2Courses.add(createCourse("Cybersécurité", bloc2Students));
        bloc2Courses.add(createCourse("Blockchain", bloc2Students));
        bloc2Courses.add(createCourse("Web Development", bloc2Students));
        bloc2Courses.add(createCourse("Machine Learning", bloc2Students));
        bloc2Courses.add(createCourse("Bases de données NoSQL", bloc2Students));
        bloc2Courses.add(createCourse("Administration de systèmes", bloc2Students));

        blocs.add(new NewBloc("Bloc 2", bloc2Students, bloc2Courses));

        // Bloc 3 - Informatique
        List<NewCourse> bloc3Courses = new ArrayList<>();
        Map<String, NewStudent> bloc3Students = createStudents("Isaac", "Jack", "Katherine", "Laura");

        // Ajout de 10 cours similaires pour le Bloc 3
        bloc3Courses.add(createCourse("Programmation C++", bloc3Students));
        bloc3Courses.add(createCourse("Administration réseau", bloc3Students));
        bloc3Courses.add(createCourse("Ingénierie logicielle", bloc3Students));
        bloc3Courses.add(createCourse("Systèmes distribués", bloc3Students));
        bloc3Courses.add(createCourse("Calcul haute performance", bloc3Students));
        bloc3Courses.add(createCourse("Interface utilisateur", bloc3Students));
        bloc3Courses.add(createCourse("Virtualisation", bloc3Students));
        bloc3Courses.add(createCourse("Cloud et SaaS", bloc3Students));
        bloc3Courses.add(createCourse("Traitement d'image", bloc3Students));
        bloc3Courses.add(createCourse("Test et validation logicielle", bloc3Students));

        blocs.add(new NewBloc("Bloc 3", bloc3Students, bloc3Courses));

        return blocs;
    }

    // Méthode pour créer un cours avec des évaluations et des étudiants
    private static NewCourse createCourse(String courseName, Map<String, NewStudent> students) {
        List<NewEvaluation> evaluations = new ArrayList<>();

        // Ajouter 5 évaluations composites
        for (int i = 0; i < 5; i++) {
            evaluations.add(createCompositeEvaluation(courseName + " Composite " + (i + 1), 100, students));
        }

        // Ajouter 5 évaluations finales
        for (int i = 0; i < 5; i++) {
            evaluations.add(new FinalEvaluation(courseName + " Final " + (i + 1), 100, students));
        }

        return new NewCourse(courseName, students, evaluations);
    }

    private static Map<String, NewStudent> createStudents(String... names) {
        Map<String, NewStudent> students = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            String matricule = "la" + String.format("%04d", i + 1);  // Format matricule à 4 chiffres avec "la"
            students.put(matricule, new NewStudent(matricule, names[i], "Lastname"));
        }
        return students;
    }

    // Méthode pour créer une évaluation composite avec 10 sous-évaluations
    private static CompositeEvaluation createCompositeEvaluation(String name, int maxPoint, Map<String, NewStudent> students) {
        CompositeEvaluation composite = new CompositeEvaluation(name, maxPoint, students);

        // Ajouter 10 sous-évaluations
        for (int i = 0; i < 10; i++) {
            composite.addEvaluation(new FinalEvaluation("Sous-évaluation " + (i + 1), maxPoint / 10, students));
        }

        return composite;
    }
}
