package be.com.learn.adminsys.b3q1_androidproject_jm.Views.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.GradeDetailController;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class GradeDetailFragment extends Fragment {

    private GradeDetailController gradeController;

    private TextView textViewGradeTitle;
    private TextView textViewStudentLastName;
    private TextView textViewStudentFirstName;
    private TextView textViewStudentMatricule;
    private EditText editTextGradePoint;
    private TextView textViewMaxPoints;
    private Button buttonSaveGrade;
    private Button buttonBack;
    private CheckBox checkBoxForceGrade;
    private TextView checkBoxForceGradeText;

    private int gradeId; // ID du grade

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grade_detail, container, false);

        // Initialisation des vues
        textViewGradeTitle = view.findViewById(R.id.textViewGradeTitle);
        textViewStudentLastName = view.findViewById(R.id.textViewStudentLastName);
        textViewStudentFirstName = view.findViewById(R.id.textViewStudentFirstName);
        textViewStudentMatricule = view.findViewById(R.id.textViewStudentMatricule);
        editTextGradePoint = view.findViewById(R.id.editTextGradePoint);
        textViewMaxPoints = view.findViewById(R.id.textViewMaxPoints);
        buttonSaveGrade = view.findViewById(R.id.buttonSaveGrade);
        buttonBack = view.findViewById(R.id.buttonBack);
        checkBoxForceGrade = view.findViewById(R.id.checkBoxForceGrade);
        checkBoxForceGradeText = view.findViewById(R.id.checkBoxForceGradeText);

        // Initialisation du controller
        gradeController = new GradeDetailController(this);

        // Récupérer les arguments
        if (getArguments() != null) {
            gradeId = getArguments().getInt("gradeId");
        }

        // Charger les détails de la note
        gradeController.loadGradeDetails(gradeId);

        // Gérer les interactions
        buttonSaveGrade.setOnClickListener(v -> gradeController.saveGrade(gradeId, editTextGradePoint.getText().toString(), checkBoxForceGrade.isChecked()));
        buttonBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    // Méthode pour mettre à jour la vue depuis le contrôleur
    public void updateGradeDetails(String studentLastName, String studentFirstName, String studentMatricule, String gradePoint, String gradeTitle, String maxPoints, boolean isFinalEvaluation, boolean isForced) {
        // Mise à jour des champs avec les valeurs récupérées
        textViewStudentLastName.setText(studentLastName);
        textViewStudentFirstName.setText(studentFirstName);
        textViewStudentMatricule.setText(studentMatricule);
        editTextGradePoint.setText(gradePoint);
        textViewGradeTitle.setText(gradeTitle);
        textViewMaxPoints.setText(maxPoints);

        if (isFinalEvaluation) {
            checkBoxForceGrade.setVisibility(View.GONE);
            checkBoxForceGradeText.setVisibility(View.GONE);
        } else {
            checkBoxForceGrade.setVisibility(View.VISIBLE);
            checkBoxForceGradeText.setVisibility(View.VISIBLE);
        }

        checkBoxForceGrade.setChecked(isForced);
    }

    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showSuccess(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
