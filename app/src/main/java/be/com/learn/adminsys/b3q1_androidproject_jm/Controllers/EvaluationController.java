package be.com.learn.adminsys.b3q1_androidproject_jm.Controllers;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.CourseManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.EvaluationManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Adapter.EvaluationAdapter;

import java.util.List;
import java.util.concurrent.Executors;

public class EvaluationController {

    private EvaluationManager evaluationManager;
    private FragmentActivity activity;
    private EvaluationAdapter evaluationAdapter;

    private int parentId;
    private String parentType;

    public EvaluationController(EvaluationManager evaluationManager, FragmentActivity activity, EvaluationAdapter evaluationAdapter, int parentId, String parentType) {
        this.evaluationManager = evaluationManager;
        this.activity = activity;
        this.evaluationAdapter = evaluationAdapter;
        this.parentId = parentId;
        this.parentType = parentType;
    }

    // Charger les évaluations depuis le modèle et les mettre à jour dans la vue
    public void loadEvaluations() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Evaluation> evaluations = evaluationManager.getEvaluationsByParent(parentId, parentType);
            activity.runOnUiThread(() -> evaluationAdapter.updateEvaluations(evaluations));
        });
    }

    // Ajouter une évaluation et mettre à jour la vue
    public void addEvaluation(int maxPoints, String evaluationName,String type) {
        if (!evaluationName.isEmpty()) {
            Executors.newSingleThreadExecutor().execute(() -> {
                Evaluation evaluation = new Evaluation(maxPoints, evaluationName,parentType ,parentId, type);
                evaluationManager.addEvaluation(evaluation);
                loadEvaluations();  // Recharger les évaluations après ajout
            });
        } else {
            activity.runOnUiThread(() -> Toast.makeText(activity, "L'évaluation ne peut pas être vide", Toast.LENGTH_SHORT).show());
        }
    }
    public void setParentName(View view) {
        Executors.newSingleThreadExecutor().execute(() -> {
            String parentName;

            if ("Course".equals(parentType)) {
                CourseManager courseManager = new CourseManager(AppDatabase.getInstance(activity));
                parentName = "Evaluation du cours : " + courseManager.getCourseById(parentId).getName();
            } else {
                parentName = "Sous-évaluation de : " + evaluationManager.getEvaluationById(parentId).getName();
            }

            activity.runOnUiThread(() -> {
                TextView textEvaluationTitle = view.findViewById(R.id.textEvaluationTitle);
                textEvaluationTitle.setText(parentName);
            });
        });
    }
}
