package be.com.learn.adminsys.b3q1_androidproject_jm.Fragment;

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

import be.com.learn.adminsys.b3q1_androidproject_jm.Controller.CourseAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Controller.StudentAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewBloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewCourse;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewStudent;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class CourseFragment extends Fragment {

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private StudentAdapter studentAdapter;
    private NewBloc selectedBloc;
    private Switch toggleButton;
    private Map<String, NewStudent> studentsMap;  // Utilisation d'une Map au lieu d'une List
    private Button addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        addButton = view.findViewById(R.id.addCourseButton); // Référence au bouton "Add"
        TextView textCourseTitle = view.findViewById(R.id.textCourseTitle);
        // Récupère le bloc sélectionné depuis les arguments
        if (getArguments() != null) {
            selectedBloc = (NewBloc) getArguments().getSerializable("selectedBloc");
        }
        if (selectedBloc != null) {
            textCourseTitle.setText("Cours de : " + selectedBloc.getName());
        }
        // Initialiser la map des étudiants si le bloc sélectionné en contient des étudiants
        if (selectedBloc != null) {
            studentsMap = selectedBloc.getStudents(); // Supposons que NewBloc a une méthode getStudents() qui retourne une Map
        } else {
            studentsMap = new HashMap<>();
        }

        // Initialiser l'adaptateur pour les cours
        loadCourses();

        // Initialiser l'adaptateur pour les étudiants (en utilisant la Map)
        studentAdapter = new StudentAdapter(new ArrayList<>(studentsMap.values()));

        // Set the listener for the toggle button
        toggleButton = view.findViewById(R.id.toggleButtonBlocStudent);
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Afficher les étudiants
                recyclerView.setAdapter(studentAdapter);
                addButton.setText("Ajouter un étudiant");  // Changer le texte du bouton
            } else {
                // Afficher les cours
                loadCourses();
                addButton.setText("Ajouter un cours"); // Changer le texte du bouton
            }
        });

        // Ajouter un bouton pour ouvrir la popup d'ajout
        addButton.setOnClickListener(v -> {
            if (toggleButton.isChecked()) {
                showAddStudentDialog(v);  // Ouvrir la popup pour ajouter un étudiant
            } else {
                showAddCourseDialog(v);   // Ouvrir la popup pour ajouter un cours
            }
        });

        return view;
    }

    private void loadCourses() {
        if (selectedBloc != null) {
            List<NewCourse> courses = selectedBloc.getCourses();
            courseAdapter = new CourseAdapter(courses, course -> navigateToEvaluations(course));
            recyclerView.setAdapter(courseAdapter);
        }
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
                // Crée un nouveau cours avec le nom saisi
                NewCourse newCourse = new NewCourse(courseName, new HashMap<>(), new ArrayList<>());
                // Ajoute le cours à la liste des cours dans le bloc sélectionné
                selectedBloc.getCourses().add(newCourse);
                courseAdapter.notifyDataSetChanged(); // Met à jour l'adaptateur de RecyclerView
                popupWindow.dismiss();
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
                // Crée un nouveau student avec les informations saisies
                NewStudent newStudent = new NewStudent(matricule, firstName, lastName);
                studentsMap.put(matricule, newStudent);  // Utilisation de la clé matricule pour ajouter à la Map
                studentAdapter.notifyDataSetChanged();  // Met à jour l'adaptateur de RecyclerView
                popupWindow.dismiss();
            } else {
                Toast.makeText(requireContext(), "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            }
        });

        cancelStudentButton.setOnClickListener(view -> popupWindow.dismiss());
    }

    private void navigateToEvaluations(NewCourse course) {
        // Naviguer vers EvaluationFragment avec le cours sélectionné
        Bundle args = new Bundle();
        args.putSerializable("selectedParent", course);
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
