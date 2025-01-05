package be.com.learn.adminsys.b3q1_androidproject_jm.Fragments;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.CourseAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.StudentAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Course;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class CourseFragment extends Fragment {

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private StudentAdapter studentAdapter;
    private Bloc selectedBloc;
    private Switch toggleButton;
    private Map<String, Student> studentsMap;
    private Button addButton;
    private int selectedBlocId;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        db = AppDatabase.getInstance(requireContext());

        addButton = view.findViewById(R.id.addCourseButton);
        TextView textCourseTitle = view.findViewById(R.id.textCourseTitle);

        if (getArguments() != null) {
            selectedBloc = (Bloc) getArguments().getSerializable("selectedBloc");
        }
        if (selectedBloc != null) {
            textCourseTitle.setText("Cours de : " + selectedBloc.getName());
            loadCourses();
        } else {
            studentsMap = new HashMap<>();
        }


        toggleButton = view.findViewById(R.id.toggleButtonBlocStudent);
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                loadStudents();
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

    private void loadCourses() {
        if (selectedBloc != null) {
            Executors.newSingleThreadExecutor().execute(() -> {
                List<Course> courses = db.courseDao().getCoursesByBlocId(selectedBloc.getId());
                requireActivity().runOnUiThread(() -> {
                    courseAdapter = new CourseAdapter(courses, course -> navigateToEvaluations(course));
                    recyclerView.setAdapter(courseAdapter);
                });
            });
        }
    }

    private void loadStudents() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Student> students = db.studentDao().getStudentsByBlocId(selectedBloc.getId());
            studentsMap = new HashMap<>();
            for (Student student : students) {
                studentsMap.put(student.getMatricule(), student);
            }

            requireActivity().runOnUiThread(() -> {
                studentAdapter = new StudentAdapter(new ArrayList<>(studentsMap.values()));
                recyclerView.setAdapter(studentAdapter);
            });
        });
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
                    Course newCourse = new Course(courseName, selectedBloc.getId());
                    db.courseDao().insert(newCourse);

                    requireActivity().runOnUiThread(() -> {
                        loadCourses();
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
                    Student newStudent = new Student(matricule, firstName, lastName, selectedBloc.getId());
                    db.studentDao().insert(newStudent);
                    requireActivity().runOnUiThread(() -> {
                        loadStudents();
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
