// WorkoutPlanAdapter.java

package com.example.applicatiofirst.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.applicatiofirst.R;
import com.example.applicatiofirst.model.WorkoutPlan;

import java.util.List;

public class WorkoutPlanAdapter extends RecyclerView.Adapter<WorkoutPlanAdapter.ViewHolder> {

    private List<WorkoutPlan> workoutPlans;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;

    public WorkoutPlanAdapter(List<WorkoutPlan> workoutPlans,
                              OnEditClickListener editClickListener,
                              OnDeleteClickListener deleteClickListener) {
        this.workoutPlans = workoutPlans;
        this.editClickListener = editClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutPlan workoutPlan = workoutPlans.get(position);
        holder.wpNameTextView.setText(workoutPlan.getWpName());
        holder.timeGoalTextView.setText(workoutPlan.getTimeGoal());
        holder.typeTextView.setText(workoutPlan.getType());

        holder.editWorkoutButton.setOnClickListener(view -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(position, workoutPlan);
            }
        });

        holder.deleteWorkoutButton.setOnClickListener(view -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(position, workoutPlan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutPlans.size();
    }

    public void updateWorkoutPlans(List<WorkoutPlan> newWorkoutPlans) {
        workoutPlans.clear();
        workoutPlans.addAll(newWorkoutPlans);
        notifyDataSetChanged();
    }

    public interface OnEditClickListener {
        void onEditClick(int position, WorkoutPlan workoutPlan);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position, WorkoutPlan workoutPlan);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wpNameTextView;
        TextView typeTextView;
        TextView timeGoalTextView;
        ImageButton editWorkoutButton;
        ImageButton deleteWorkoutButton;

        public ViewHolder(View itemView) {
            super(itemView);
            wpNameTextView = itemView.findViewById(R.id.workoutNameTextView);
            typeTextView = itemView.findViewById(R.id.workoutTypeTextView);
            timeGoalTextView = itemView.findViewById(R.id.timeGoalTextView);
            editWorkoutButton = itemView.findViewById(R.id.editWorkoutButton);
            deleteWorkoutButton = itemView.findViewById(R.id.deleteWorkoutButton);
        }
    }
}
