package be.com.learn.adminsys.b3q1_androidproject_jm.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

import java.util.List;

public class BlocAdapter extends RecyclerView.Adapter<BlocAdapter.BlocViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String blocName);
    }

    private List<String> blocNames;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    // Constructeur
    public BlocAdapter(List<String> blocNames, OnItemClickListener listener) {
        this.blocNames = blocNames;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BlocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Créer la vue pour chaque élément de la liste en utilisant item_bloc.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bloc, parent, false);
        return new BlocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlocViewHolder holder, int position) {
        String blocName = blocNames.get(position);
        holder.bind(blocName, listener, position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return blocNames.size();
    }

    public class BlocViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Button btnViewCourses;

        public BlocViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvBlocName);
            btnViewCourses = itemView.findViewById(R.id.btnViewCourses);
        }

        public void bind(final String blocName, final OnItemClickListener listener, boolean isSelected) {
            textView.setText(blocName);
            btnViewCourses.setVisibility(isSelected ? View.VISIBLE : View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int previousPosition = selectedPosition;
                    selectedPosition = getAdapterPosition();

                    // Notifier la mise à jour pour masquer le bouton du bloc précédent
                    notifyItemChanged(previousPosition);
                    // Notifier la mise à jour pour montrer le bouton du bloc sélectionné
                    notifyItemChanged(selectedPosition);

                    listener.onItemClick(blocName);
                }
            });

            btnViewCourses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lancer une action pour voir les cours associés au bloc
                    listener.onItemClick(blocName);
                }
            });
        }
    }
}
