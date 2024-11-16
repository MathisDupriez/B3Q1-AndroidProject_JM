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

import be.com.learn.adminsys.b3q1_androidproject_jm.Controller.CourseAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewBloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewCourse;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class CourseFragment extends Fragment {

    private RecyclerView recyclerViewCourses;
    private CourseAdapter courseAdapter;
    private NewBloc selectedBloc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        recyclerViewCourses = view.findViewById(R.id.recyclerViewCourses);
        recyclerViewCourses.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Récupère le bloc sélectionné depuis les arguments
        if (getArguments() != null) {
            selectedBloc = (NewBloc) getArguments().getSerializable("selectedBloc");
        }

        loadCourses();
        return view;
    }

    private void loadCourses() {
        if (selectedBloc != null) {
            List<NewCourse> courses = selectedBloc.getCourses();
            courseAdapter = new CourseAdapter(courses, course -> navigateToEvaluations(course));
            recyclerViewCourses.setAdapter(courseAdapter);
        }
    }

    private void navigateToEvaluations(NewCourse course) {
        // Naviguer vers EvaluationFragment avec le cours sélectionné
        Bundle args = new Bundle();
        args.putSerializable("selectedCourse", course);
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
