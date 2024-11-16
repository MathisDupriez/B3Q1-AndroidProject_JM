package be.com.learn.adminsys.b3q1_androidproject_jm;

import org.junit.Test;

import java.util.*;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.*;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.*;

import static org.junit.Assert.*;

/**
 * Test : ajout d'un étudiant au bloc, propagation aux cours et évaluations, et affichage des notes.
 */
public class ModelUnitTest {
    @Test
    public void testAddStudentToBlocWithGrades() {
        // Création des étudiants
        NewStudent student1 = new NewStudent("S001", "Alice", "Dupont");
        NewStudent student2 = new NewStudent("S002", "Bob", "Martin");
        NewStudent student3 = new NewStudent("S003", "Charlie", "Durand");

        // Ajout des étudiants dans une map
        Map<String, NewStudent> studentsMap = new HashMap<>();
        studentsMap.put(student1.getMatricule(), student1);
        studentsMap.put(student2.getMatricule(), student2);
        studentsMap.put(student3.getMatricule(), student3);

        // Création du bloc avec les étudiants
        List<NewCourse> courses = new ArrayList<>();
        NewBloc bloc1 = new NewBloc("Bloc 1", studentsMap, courses);

        // Création d'un cours en utilisant les étudiants du bloc
        NewCourse mathCourse = new NewCourse("Mathématiques", bloc1.getStudents(), new ArrayList<>());

        // Création des notes pour une évaluation finale
        FinalEvaluation finalExam = new FinalEvaluation("Examen final", 100, mathCourse.getStudents());
        finalExam.AddGrade(new NewGrade(student1, 80)); // Alice: 80
        finalExam.AddGrade(new NewGrade(student2, 70)); // Bob: 70
        finalExam.AddGrade(new NewGrade(student3, 90)); // Charlie: 90

        // Création d'une évaluation composite
        CompositeEvaluation compositeEval = new CompositeEvaluation("Travaux pratiques", 50, mathCourse.getStudents());
        compositeEval.AddEvaluation(finalExam); // Ajout de l'évaluation finale à l'évaluation composite

        // Ajout des évaluations au cours
        mathCourse.addEvaluation(finalExam);
        mathCourse.addEvaluation(compositeEval);

        // Ajout du cours au bloc
        bloc1.addCourse(mathCourse);

        // Vérification initiale
        assertEquals(3, bloc1.getStudents().size());
        assertEquals(1, bloc1.getCourses().size());
        assertEquals(3, mathCourse.getStudents().size());

        // Vérification des moyennes
        double finalExamAverage = finalExam.GetAverage();
        double compositeEvalAverage = compositeEval.GetAverage();

        assertEquals(80, finalExamAverage, 0.01); // Moyenne attendue : (80+70+90)/3 = 80
        assertEquals(40, compositeEvalAverage, 0.01); // Moyenne composite basée sur l'évaluation finale

        // Affichage des détails avec les notes
        System.out.println("Avant ajout d'un étudiant :");
        printBlocDetailsWithGrades(bloc1);

        // Ajout d'un nouvel étudiant dans le bloc
        NewStudent student4 = new NewStudent("S004", "Diane", "Moreau");
        bloc1.addStudent(student4);

        // Vérification après ajout
        assertEquals(4, bloc1.getStudents().size());
        assertEquals(4, mathCourse.getStudents().size());

        // Affichage après ajout
        System.out.println("Après ajout d'un étudiant :");
        printBlocDetailsWithGrades(bloc1);

        System.out.println("Test terminé avec succès.");
    }

    // Méthode pour afficher les détails d'un bloc avec les notes
    private void printBlocDetailsWithGrades(NewBloc bloc) {
        System.out.println("Bloc : " + bloc.getName());
        System.out.println("Étudiants dans le bloc :");
        for (Map.Entry<String, NewStudent> entry : bloc.getStudents().entrySet()) {
            NewStudent student = entry.getValue();
            System.out.println(" - " + student.getMatricule() + ": " + student.getFirstName() + " " + student.getLastName());
        }

        System.out.println("Cours dans le bloc :");
        for (NewCourse course : bloc.getCourses()) {
            System.out.println(" - " + course.getName());
            System.out.println("   Étudiants dans le cours :");
            for (Map.Entry<String, NewStudent> entry : course.getStudents().entrySet()) {
                NewStudent student = entry.getValue();
                System.out.println("     * " + student.getMatricule() + ": " + student.getFirstName() + " " + student.getLastName());
            }
            System.out.println("   Évaluations dans le cours :");
            for (NewEvaluation eval : course.getEvaluations()) {
                System.out.println("     - Évaluation : " + eval.getName() + " (" + eval.getClass().getSimpleName() + ")");
                System.out.println("       MaxPoint : " + eval.getMaxPoint());
                if (eval instanceof FinalEvaluation) {
                    FinalEvaluation finalEval = (FinalEvaluation) eval;
                    System.out.println("       Notes des étudiants :");
                    for (NewGrade grade : finalEval.getGrades()) {
                        System.out.println("         > " + grade.getStudent().getMatricule() + ": " +
                                grade.getStudent().getFirstName() + " " + grade.getStudent().getLastName() +
                                " - Note : " + grade.getGrade());
                    }
                }
                System.out.println("       Moyenne : " + eval.GetAverage());
            }
        }
    }
}
