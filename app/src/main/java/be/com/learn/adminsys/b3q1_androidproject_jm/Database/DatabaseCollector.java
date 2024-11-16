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

        // Bloc 1
        List<NewCourse> bloc1Courses = new ArrayList<>();
        Map<String, NewStudent> bloc1Students = createStudents("Alice", "Bob", "Catherine", "David");

        // Cours 1.1
        List<NewEvaluation> course1_1Evaluations = new ArrayList<>();
        course1_1Evaluations.add(new FinalEvaluation("Exam 1 (Final)", 100, bloc1Students));
        course1_1Evaluations.add(createCompositeEvaluation("Project 1 (Composite)", 50, bloc1Students));
        bloc1Courses.add(new NewCourse("Mathematics (Course)", bloc1Students, course1_1Evaluations));

        // Cours 1.2
        List<NewEvaluation> course1_2Evaluations = new ArrayList<>();
        course1_2Evaluations.add(new FinalEvaluation("Exam 2 (Final)", 80, bloc1Students));
        course1_2Evaluations.add(createCompositeEvaluation("Lab Work (Composite)", 30, bloc1Students));
        bloc1Courses.add(new NewCourse("Physics (Course)", bloc1Students, course1_2Evaluations));

        blocs.add(new NewBloc("Bloc 1 (Bloc)", bloc1Students, bloc1Courses));

        // Bloc 2
        List<NewCourse> bloc2Courses = new ArrayList<>();
        Map<String, NewStudent> bloc2Students = createStudents("Eve", "Frank", "George", "Hannah");

        // Cours 2.1
        List<NewEvaluation> course2_1Evaluations = new ArrayList<>();
        course2_1Evaluations.add(new FinalEvaluation("Midterm (Final)", 50, bloc2Students));
        course2_1Evaluations.add(createCompositeEvaluation("Final Project (Composite)", 100, bloc2Students));
        bloc2Courses.add(new NewCourse("History (Course)", bloc2Students, course2_1Evaluations));

        // Cours 2.2
        List<NewEvaluation> course2_2Evaluations = new ArrayList<>();
        course2_2Evaluations.add(new FinalEvaluation("Quiz 1 (Final)", 20, bloc2Students));
        course2_2Evaluations.add(createCompositeEvaluation("Group Assignment (Composite)", 40, bloc2Students));
        bloc2Courses.add(new NewCourse("Chemistry (Course)", bloc2Students, course2_2Evaluations));

        blocs.add(new NewBloc("Bloc 2 (Bloc)", bloc2Students, bloc2Courses));

        // Bloc 3
        List<NewCourse> bloc3Courses = new ArrayList<>();
        Map<String, NewStudent> bloc3Students = createStudents("Isaac", "Jack", "Katherine", "Laura");

        // Cours 3.1
        List<NewEvaluation> course3_1Evaluations = new ArrayList<>();
        course3_1Evaluations.add(new FinalEvaluation("Oral Exam (Final)", 20, bloc3Students));
        course3_1Evaluations.add(createCompositeEvaluation("Creative Writing (Composite)", 30, bloc3Students));
        bloc3Courses.add(new NewCourse("Literature (Course)", bloc3Students, course3_1Evaluations));

        blocs.add(new NewBloc("Bloc 3 (Bloc)", bloc3Students, bloc3Courses));

        return blocs;
    }

    private static Map<String, NewStudent> createStudents(String... names) {
        Map<String, NewStudent> students = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            String matricule = String.valueOf(i + 1);
            students.put(matricule, new NewStudent(matricule, names[i], "Lastname"));
        }
        return students;
    }

    private static CompositeEvaluation createCompositeEvaluation(String name, int maxPoint, Map<String, NewStudent> students) {
        CompositeEvaluation composite = new CompositeEvaluation(name, maxPoint, students);

        // Ajouter des évaluations finales dans la composite
        composite.AddEvaluation(new FinalEvaluation("Sub Exam 1 (Final)", maxPoint / 2, students));
        composite.AddEvaluation(new FinalEvaluation("Sub Exam 2 (Final)", maxPoint / 2, students));

        return composite;
    }
}
