package be.com.learn.adminsys.b3q1_androidproject_jm.Controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import be.com.learn.adminsys.b3q1_androidproject_jm.Fragment.BlocFragment;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Charger le fragment initial (BlocFragment) si ce n'est pas déjà fait
        if (savedInstanceState == null) {
            loadFragment(new BlocFragment());
        }
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
