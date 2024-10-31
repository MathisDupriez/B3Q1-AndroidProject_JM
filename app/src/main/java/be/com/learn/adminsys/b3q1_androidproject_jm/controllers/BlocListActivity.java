package be.com.learn.adminsys.b3q1_androidproject_jm.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;
import be.com.learn.adminsys.b3q1_androidproject_jm.view.MainViewController;

import java.util.List;

public class BlocListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBlocs;
    private MainViewController mainViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloc_list);

        recyclerViewBlocs = findViewById(R.id.recyclerViewBlocs);
        mainViewController = new MainViewController();

        List<String> blocNames = mainViewController.getBlocNames();
        recyclerViewBlocs.setLayoutManager(new LinearLayoutManager(this));

        BlocAdapter adapter = new BlocAdapter(blocNames, new BlocAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String blocName) {
                Log.d("BlocListActivity", "Bloc sélectionné : " + blocName);
                Intent intent = new Intent(BlocListActivity.this, CourseListActivity.class);
                intent.putExtra("selectedBloc", blocName);
                startActivity(intent);
            }
        });
        recyclerViewBlocs.setAdapter(adapter);
    }
}
