package be.com.learn.adminsys.b3q1_androidproject_jm.Controllers;

import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.CourseManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Course;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Adapter.CourseAdapter;

import java.util.List;
import java.util.concurrent.Executors;

public class CourseController {

    private CourseManager courseManager;
    private FragmentActivity activity;
    private CourseAdapter courseAdapter;

    public CourseController(CourseManager courseManager, FragmentActivity activity, CourseAdapter courseAdapter) {
        this.courseManager = courseManager;
        this.activity = activity;
        this.courseAdapter = courseAdapter;
    }

    // Charger les cours depuis le modèle et les mettre à jour dans la vue
    public void loadCourses(int blocId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Course> courses = courseManager.getCoursesByBlocId(blocId);
            activity.runOnUiThread(() -> courseAdapter.updateCourses(courses));
        });
    }

    // Ajouter un cours et mettre à jour la vue
    public void addCourse(String courseName, int blocId) {
        if (!courseName.isEmpty()) {
            Executors.newSingleThreadExecutor().execute(() -> {
                courseManager.addCourse(courseName, blocId);
                loadCourses(blocId);  // Recharger les cours après ajout
            });
        } else {
            activity.runOnUiThread(() -> Toast.makeText(activity, "Le nom du cours ne peut pas être vide", Toast.LENGTH_SHORT).show());
        }
    }
}
