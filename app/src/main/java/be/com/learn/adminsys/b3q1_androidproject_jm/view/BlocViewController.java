package be.com.learn.adminsys.b3q1_androidproject_jm.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import be.com.learn.adminsys.b3q1_androidproject_jm.models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class BlocViewController {

    private final RecyclerView recyclerViewBlocs;
    private final Context context;

    private final Button mBtnAddBloc;
    private final Button mBtnModifyStudent;


    public BlocViewController(View rootView, Context context) {
        this.recyclerViewBlocs = rootView.findViewById(R.id.recyclerViewBlocs);
        this.recyclerViewBlocs.setLayoutManager(new LinearLayoutManager(context));
        this.context = context;

        this.mBtnAddBloc = rootView.findViewById(R.id.btnAddBloc);
        this.mBtnModifyStudent = rootView.findViewById(R.id.btnModifyStudent);

    }

    private void setListeners(BlocViewControllerInterface listener) {
        mBtnAddBloc.setOnClickListener(v -> listener.btnAddBlocClicked());
        mBtnModifyStudent.setOnClickListener(v -> listener.btnModifyStudentClicked());
    }

    // Implémentation des méthodes de l'interface
    public void afficherBlocs(List<Bloc> blocs, RecyclerView.Adapter adapter) {
        recyclerViewBlocs.setAdapter(adapter);
    }

    public void afficherMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }



    // Interface définissant les méthodes de la vue
    public interface BlocViewControllerInterface {
        void btnAddBlocClicked();
        void btnModifyStudentClicked();
    }
}
