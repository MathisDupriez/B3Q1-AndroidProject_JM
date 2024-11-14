package be.com.learn.adminsys.b3q1_androidproject_jm.oldControllers;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {
    // CORRECTION, pretty good, it manage the data, we need to setup a viewController for this activity
    private ListView listViewStudents;
    private List<String> studentNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        // CORRECTING this is the part of the code that we need to correct and move to a viewController
        listViewStudents = findViewById(R.id.listViewStudents);

        // Initialiser les étudiants pour tester
        studentNames = new ArrayList<>();
        studentNames.add("Alice Dupont");
        studentNames.add("Bob Martin");
        studentNames.add("Claire Durand");

        // Créer un adaptateur pour remplir la liste des étudiants
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        listViewStudents.setAdapter(adapter);
    }
}
