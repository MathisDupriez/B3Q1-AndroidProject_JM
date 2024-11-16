package be.com.learn.adminsys.b3q1_androidproject_jm.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Controller.EvaluationAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.CompositeEvaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.NewEvaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewCourse;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class EvaluationFragment extends Fragment {

    private RecyclerView recyclerViewEvaluations;
    private EvaluationAdapter evaluationAdapter;
    private NewCourse selectedCourse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        recyclerViewEvaluations = view.findViewById(R.id.recyclerViewEvaluations);
        recyclerViewEvaluations.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Récupère le cours sélectionné depuis les arguments
        if (getArguments() != null) {
            selectedCourse = (NewCourse) getArguments().getSerializable("selectedCourse");
        }

        loadEvaluations();
        return view;
    }

    private void loadEvaluations() {
        if (selectedCourse != null) {
            List<NewEvaluation> evaluations = selectedCourse.getEvaluations();
            evaluationAdapter = new EvaluationAdapter(evaluations, evaluation -> {
                if (evaluation instanceof CompositeEvaluation) {
                    navigateToSubEvaluations((CompositeEvaluation) evaluation);
                }
            });
            recyclerViewEvaluations.setAdapter(evaluationAdapter);
        }
    }

    private void navigateToSubEvaluations(CompositeEvaluation compositeEvaluation) {
        // Naviguer vers un nouveau fragment pour afficher les sous-évaluations
        Bundle args = new Bundle();
        args.putSerializable("selectedCompositeEvaluation", compositeEvaluation);
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
