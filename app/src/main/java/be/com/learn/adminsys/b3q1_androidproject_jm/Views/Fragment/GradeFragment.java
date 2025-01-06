package be.com.learn.adminsys.b3q1_androidproject_jm.Views.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.GradeController;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Fragment.GradeDetailFragment;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.EvaluationManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.GradeManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.StudentManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Adapter.GradeAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class GradeFragment extends Fragment {

    private RecyclerView recyclerViewGrades;
    private AppDatabase db;
    private String evaluationName;

    private GradeController gradeController;

    private GradeManager gradeManager;
    private EvaluationManager evaluationManager;
    private StudentManager studentManager;

    private GradeAdapter gradeAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grade, container, false);
        recyclerViewGrades = view.findViewById(R.id.recyclerViewGrades);
        recyclerViewGrades.setLayoutManager(new LinearLayoutManager(requireContext()));
        gradeAdapter = new GradeAdapter(List.of(), requireActivity(), grade -> navigateToGradeDetail(grade.getId()));
        recyclerViewGrades.setAdapter(gradeAdapter);

        db = AppDatabase.getInstance(requireContext());
        gradeManager = new GradeManager(db);
        evaluationManager = new EvaluationManager(db);
        studentManager = new StudentManager(db);

        // Récupérer les arguments
        if (getArguments() != null) {
            gradeController = new GradeController(gradeManager,evaluationManager, studentManager ,requireActivity(), gradeAdapter, getArguments().getInt("evaluationId"),(Bloc) getArguments().getSerializable("selectedBloc"),0);
        }

        // Mettre à jour le titre
        setEvaluationName();
        gradeController.loadFinalGrades();
        return view;
    }

    private void setEvaluationName() {
        Executors.newSingleThreadExecutor().execute(() -> {
            evaluationName = db.evaluationDao().getEvaluationById(getArguments().getInt("evaluationId")).getName();

            requireActivity().runOnUiThread(() -> {
                TextView textGradeTitle = requireView().findViewById(R.id.textGradeTitle);
                textGradeTitle.setText("Notes pour l'évaluation : " + evaluationName);
            });
        });
    }





    private void navigateToGradeDetail(int gradeId) {
        Bundle args = new Bundle();
        args.putInt("gradeId", gradeId);

        GradeDetailFragment fragment = new GradeDetailFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
