package be.com.learn.adminsys.b3q1_androidproject_jm.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class MainActivity extends AppCompatActivity {

    private Button btnViewBlocs, btnViewCourses, btnViewStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser les boutons
        btnViewBlocs = findViewById(R.id.btnViewBlocs);
        btnViewCourses = findViewById(R.id.btnViewCourses);
        btnViewStudents = findViewById(R.id.btnViewStudents);

        // Ajouter des listeners pour naviguer vers d'autres activités
        btnViewBlocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Bouton Voir les Blocs cliqué");
                Intent intent = new Intent(MainActivity.this, BlocListActivity.class);
                startActivity(intent);
            }
        });

        btnViewCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Bouton Voir les Cours cliqué");
                Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
                startActivity(intent);
            }
        });


        btnViewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Bouton Voir les Étudiants cliqué");
                Intent intent = new Intent(MainActivity.this, StudentListActivity.class);
                startActivity(intent);
            }
        });
    }
}
