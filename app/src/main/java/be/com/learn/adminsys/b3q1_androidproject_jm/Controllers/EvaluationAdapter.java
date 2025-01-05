package be.com.learn.adminsys.b3q1_androidproject_jm.Controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.EvaluationViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Evaluation evaluation);
    }

    private final List<Evaluation> evaluations;
    private final OnItemClickListener listener;

    public EvaluationAdapter(List<Evaluation> evaluations, OnItemClickListener listener) {
        this.evaluations = evaluations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EvaluationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_evaluation, parent, false);
        return new EvaluationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationViewHolder holder, int position) {
        Evaluation evaluation = evaluations.get(position);
        holder.bind(evaluation);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(evaluation));
    }

    @Override
    public int getItemCount() {
        return evaluations.size();
    }

    static class EvaluationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewType;
        TextView textViewMaxPoints;

        EvaluationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewEvaluationName);

            textViewMaxPoints = itemView.findViewById(R.id.textViewEvaluationMaxPoints);
        }

        void bind(Evaluation evaluation) {
            textViewName.setText(evaluation.getName());
            textViewMaxPoints.setText(String.valueOf(evaluation.getMaxPoints()));
        }
    }
}
