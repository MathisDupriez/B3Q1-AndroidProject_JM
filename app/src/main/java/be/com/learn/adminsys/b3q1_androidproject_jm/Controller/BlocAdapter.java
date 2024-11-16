package be.com.learn.adminsys.b3q1_androidproject_jm.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewBloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class BlocAdapter extends RecyclerView.Adapter<BlocAdapter.BlocViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(NewBloc bloc);
    }

    private final List<NewBloc> blocs;
    private final OnItemClickListener listener;

    public BlocAdapter(List<NewBloc> blocs, OnItemClickListener listener) {
        this.blocs = blocs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BlocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bloc, parent, false);
        return new BlocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlocViewHolder holder, int position) {
        NewBloc bloc = blocs.get(position);
        holder.textViewBlocName.setText(bloc.getName());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(bloc));
    }

    @Override
    public int getItemCount() {
        return blocs.size();
    }

    public static class BlocViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBlocName;

        public BlocViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBlocName = itemView.findViewById(R.id.textViewBlocName);
        }
    }
}
