package be.com.learn.adminsys.b3q1_androidproject_jm.Views.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> students;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.textViewStudentName.setText(student.getMatricule() + " : " + student.getFirstName() + " " + student.getLastName());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    // Méthode pour mettre à jour la liste des étudiants
    public void updateStudents(List<Student> newStudents) {
        this.students = newStudents;
        notifyDataSetChanged();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewStudentName;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStudentName = itemView.findViewById(R.id.textViewStudentName);
        }
    }
}
