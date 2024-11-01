package be.com.learn.adminsys.b3q1_androidproject_jm.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;
import be.com.learn.adminsys.b3q1_androidproject_jm.models.Bloc;

import java.util.List;

public class BlocAdapter extends RecyclerView.Adapter<BlocAdapter.BlocViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Bloc bloc);
    }

    private List<Bloc> blocs;
    private OnItemClickListener listener;

    public BlocAdapter(List<Bloc> blocs, OnItemClickListener listener) {
        this.blocs = blocs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BlocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bloc, parent, false);
        return new BlocViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BlocViewHolder holder, int position) {
        Bloc bloc = blocs.get(position);
        holder.textViewBlocName.setText(bloc.getName());

        // Afficher l'indicateur si le bloc a des sous-blocs
        if (bloc.getSubBlocs() != null && !bloc.getSubBlocs().isEmpty()) {
            holder.imageViewIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewIndicator.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(bloc));
    }

    @Override
    public int getItemCount() {
        return blocs.size();
    }

    static class BlocViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBlocName;
        ImageView imageViewIndicator;

        BlocViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBlocName = itemView.findViewById(R.id.textViewBlocName);
            imageViewIndicator = itemView.findViewById(R.id.imageViewIndicator);
        }
    }
}
