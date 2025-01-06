package be.com.learn.adminsys.b3q1_androidproject_jm.Views.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.CourseController;
import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.StudentController;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Course;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.CourseManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.StudentManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Adapter.CourseAdapter;

public class CourseFragment extends Fragment {

    private RecyclerView recyclerView;
    private Bloc selectedBloc;
    private Switch toggleButton;
    private Button addButton;
    private AppDatabase db;

    private CourseController courseController;
    private CourseManager courseManager;
    private StudentController studentController;
    private StudentManager studentManager;

    private CourseAdapter courseAdapter;
    private StudentAdapter studentAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCourses);
        addButton = view.findViewById(R.id.addCourseButton);
        TextView textCourseTitle = view.findViewById(R.id.textCourseTitle);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        courseAdapter = new CourseAdapter(new ArrayList<>(), course -> navigateToEvaluations(course));
        studentAdapter = new StudentAdapter(new ArrayList<>());
        recyclerView.setAdapter(courseAdapter);



        db = AppDatabase.getInstance(requireContext());
        courseManager = new CourseManager(db);
        courseController = new CourseController(courseManager,requireActivity(), courseAdapter);

        studentManager = new StudentManager(db);
        studentController = new StudentController(studentManager, requireActivity(), studentAdapter);

        if (getArguments() != null) {
            selectedBloc = (Bloc) getArguments().getSerializable("selectedBloc");
        }
        if (selectedBloc != null) {
            textCourseTitle.setText("Cours de : " + selectedBloc.getName());
            courseController.loadCourses(selectedBloc.getId());
        }


        toggleButton = view.findViewById(R.id.toggleButtonBlocStudent);
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                studentController.loadStudents(selectedBloc.getId());
                recyclerView.setAdapter(studentAdapter);
                textCourseTitle.setText("Étudiants de : " + selectedBloc.getName());
                addButton.setText("Ajouter un étudiant");
            } else {
                recyclerView.setAdapter(courseAdapter);
                textCourseTitle.setText("Cours de : " + selectedBloc.getName());
                addButton.setText("Ajouter un cours");
            }
        });

        addButton.setOnClickListener(v -> {
            if (toggleButton.isChecked()) {
                showAddStudentDialog(v);
            } else {
                showAddCourseDialog(v);
            }
        });

        return view;
    }
    private void showAddCourseDialog(View v) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View popupView = inflater.inflate(R.layout.popup_add_course, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        EditText editTextCourseName = popupView.findViewById(R.id.editTextCourseName);
        Button confirmCourseButton = popupView.findViewById(R.id.confirmCourseButton);
        Button cancelCourseButton = popupView.findViewById(R.id.cancelCourseButton);

        confirmCourseButton.setOnClickListener(view -> {
            String courseName = editTextCourseName.getText().toString();
            if (!courseName.isEmpty()) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    courseController.addCourse(courseName, selectedBloc.getId());
                    requireActivity().runOnUiThread(() -> {
                        popupWindow.dismiss();
                    });
                });
            } else {
                Toast.makeText(requireContext(), "Le nom du cours ne peut pas être vide", Toast.LENGTH_SHORT).show();
            }
        });

        cancelCourseButton.setOnClickListener(view -> popupWindow.dismiss());
    }

    private void showAddStudentDialog(View v) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View popupView = inflater.inflate(R.layout.popup_add_student, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        EditText editTextStudentFirstName = popupView.findViewById(R.id.editTextStudentFirstName);
        EditText editTextStudentLastName = popupView.findViewById(R.id.editTextStudentLastName);
        EditText editTextStudentMatricule = popupView.findViewById(R.id.editTextStudentMatricule);

        Button confirmStudentButton = popupView.findViewById(R.id.confirmStudentButton);
        Button cancelStudentButton = popupView.findViewById(R.id.cancelStudentButton);

        confirmStudentButton.setOnClickListener(view -> {
            String firstName = editTextStudentFirstName.getText().toString();
            String lastName = editTextStudentLastName.getText().toString();
            String matricule = editTextStudentMatricule.getText().toString();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !matricule.isEmpty()) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    studentController.addStudent(firstName, lastName, matricule, selectedBloc.getId());
                    requireActivity().runOnUiThread(() -> {
                        popupWindow.dismiss();
                    });
                });
            } else {
                Toast.makeText(requireContext(), "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            }
        });

        cancelStudentButton.setOnClickListener(view -> popupWindow.dismiss());
    }

    private void navigateToEvaluations(Course course) {
        Bundle args = new Bundle();
        args.putSerializable("selectedParent", course);
        args.putString("parentType", "Course");
        args.putSerializable("parentId", course.getId());
        args.putSerializable("selectedBloc", selectedBloc);
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
