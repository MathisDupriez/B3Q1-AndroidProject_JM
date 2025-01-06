package be.com.learn.adminsys.b3q1_androidproject_jm.Controllers;

import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.StudentManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Fragment.StudentAdapter;

import java.util.List;
import java.util.concurrent.Executors;

public class StudentController {

    private StudentManager studentManager;
    private FragmentActivity activity;
    private StudentAdapter studentAdapter;

    public StudentController(StudentManager studentManager, FragmentActivity activity, StudentAdapter studentAdapter) {
        this.studentManager = studentManager;
        this.activity = activity;
        this.studentAdapter = studentAdapter;
    }

    // Charger les étudiants depuis le modèle et les mettre à jour dans la vue
    public void loadStudents(int blocId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Student> students = studentManager.getStudentsByBlocId(blocId);
            activity.runOnUiThread(() -> studentAdapter.updateStudents(students));
        });
    }
    // Ajouter un étudiant et mettre à jour la vue
    public void addStudent(String matricule, String firstName, String lastName, int blocId) {
        if (!matricule.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty()) {
            Executors.newSingleThreadExecutor().execute(() -> {
                studentManager.addStudent(matricule, firstName, lastName, blocId);
                loadStudents(blocId);  // Recharger les étudiants après ajout
            });
        } else {
            activity.runOnUiThread(() -> Toast.makeText(activity, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show());
        }
    }
}
