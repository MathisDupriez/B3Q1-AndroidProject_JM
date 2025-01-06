package be.com.learn.adminsys.b3q1_androidproject_jm.Views.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Grade grade);
    }

    private List<Grade> grades;
    private final OnItemClickListener listener;
    private final AppDatabase db;

    public GradeAdapter(List<Grade> grades, FragmentActivity activity, OnItemClickListener listener) {
        this.grades = grades;
        this.listener = listener;
        this.db = AppDatabase.getInstance(activity);
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grade, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        Grade grade = grades.get(position);

        Executors.newSingleThreadExecutor().execute(() -> {
            Student student = db.studentDao().getStudentById(grade.getStudentId());
            Evaluation evaluation = db.evaluationDao().getEvaluationById(grade.getEvaluationId());

            holder.itemView.post(() -> holder.bind(grade, student, evaluation));
        });

        holder.itemView.setOnClickListener(v -> listener.onItemClick(grade));
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    // Méthode pour mettre à jour la liste des grades
    public void updateGrades(List<Grade> newGrades) {
        this.grades = newGrades;
        notifyDataSetChanged();
    }

    static class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewStudentName;
        TextView textViewStudentMatricule;
        TextView textViewGradePoint;

        GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStudentName = itemView.findViewById(R.id.textViewStudentName);
            textViewStudentMatricule = itemView.findViewById(R.id.textViewStudentMatricule);
            textViewGradePoint = itemView.findViewById(R.id.textViewGradePoint);
        }

        void bind(Grade grade, Student student, Evaluation evaluation) {
            if (student != null) {
                textViewStudentName.setText(student.getLastName() + " " + student.getFirstName());
                textViewStudentMatricule.setText(student.getMatricule());
            } else {
                textViewStudentName.setText("Étudiant inconnu");
                textViewStudentMatricule.setText("N/A");
            }

            if (evaluation != null) {
                textViewGradePoint.setText(grade.getDisplayPoint() + " / " + evaluation.getMaxPoints());
            } else {
                textViewGradePoint.setText(grade.getPoint() + " / Inconnu");
            }
        }
    }
}
