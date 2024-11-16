package be.com.learn.adminsys.b3q1_androidproject_jm.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.CompositeEvaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.FinalEvaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation.NewEvaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class EvaluationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(NewEvaluation evaluation);
    }

    private final List<NewEvaluation> evaluations;
    private final OnItemClickListener listener;

    private static final int VIEW_TYPE_FINAL = 1;
    private static final int VIEW_TYPE_COMPOSITE = 2;

    public EvaluationAdapter(List<NewEvaluation> evaluations, OnItemClickListener listener) {
        this.evaluations = evaluations;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (evaluations.get(position) instanceof FinalEvaluation) {
            return VIEW_TYPE_FINAL;
        } else {
            return VIEW_TYPE_COMPOSITE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FINAL) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_evaluation_final, parent, false);
            return new FinalEvaluationViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_evaluation_composite, parent, false);
            return new CompositeEvaluationViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewEvaluation evaluation = evaluations.get(position);
        if (holder instanceof FinalEvaluationViewHolder) {
            ((FinalEvaluationViewHolder) holder).bind((FinalEvaluation) evaluation);
        } else if (holder instanceof CompositeEvaluationViewHolder) {
            ((CompositeEvaluationViewHolder) holder).bind((CompositeEvaluation) evaluation);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(evaluation));
    }

    @Override
    public int getItemCount() {
        return evaluations.size();
    }

    static class FinalEvaluationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;

        FinalEvaluationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewEvaluationName);
        }

        void bind(FinalEvaluation evaluation) {
            textViewName.setText(evaluation.getName());
        }
    }

    static class CompositeEvaluationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;

        CompositeEvaluationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewEvaluationName);
        }

        void bind(CompositeEvaluation evaluation) {
            textViewName.setText(evaluation.getName());
        }
    }
}
