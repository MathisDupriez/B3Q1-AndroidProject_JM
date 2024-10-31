package be.com.learn.adminsys.b3q1_androidproject_jm.controllers;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;
import be.com.learn.adminsys.b3q1_androidproject_jm.view.MainViewController;
import be.com.learn.adminsys.b3q1_androidproject_jm.models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.models.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    private ListView listViewCourses;
    private MainViewController mainViewController;
    private List<String> courseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        listViewCourses = findViewById(R.id.listViewCourses);
        mainViewController = new MainViewController();

        String selectedBlocName = getIntent().getStringExtra("selectedBloc");
        if (selectedBlocName == null) {
            Log.e("CourseListActivity", "Bloc sélectionné est null");
            return;
        }

        Log.d("CourseListActivity", "Bloc sélectionné : " + selectedBlocName);

        Bloc selectedBloc = null;
        for (Bloc bloc : mainViewController.getBlocs()) {
            if (bloc.getName().equalsIgnoreCase(selectedBlocName)) {
                selectedBloc = bloc;
                break;
            }
        }

        if (selectedBloc == null) {
            Log.e("CourseListActivity", "Impossible de trouver le bloc sélectionné");
            return;
        }

        List<Course> courses = mainViewController.getCoursesForBloc(selectedBloc);
        courseNames = new ArrayList<>();
        for (Course course : courses) {
            courseNames.add(course.getCourseName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseNames);
        listViewCourses.setAdapter(adapter);
    }
}
