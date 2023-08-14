package com.example.applicatiofirst.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicatiofirst.R;
import com.example.applicatiofirst.adapters.WorkoutPlanAdapter;
import com.example.applicatiofirst.model.WorkoutPlan;
import com.example.applicatiofirst.util.DatabaseHelper;
import android.widget.Toast;

import java.util.List;

public class WorkoutPlanActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutPlanAdapter workoutPlanAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_plan);

        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<WorkoutPlan> workoutPlans = databaseHelper.getWorkoutPlansForUser(1);

        workoutPlanAdapter = new WorkoutPlanAdapter(workoutPlans,
                new WorkoutPlanAdapter.OnEditClickListener() {
                    @Override
                    public void onEditClick(int position, WorkoutPlan workoutPlan) {
                        Toast.makeText(WorkoutPlanActivity.this, "Edit button clicked for workout " + workoutPlan.getWpName(), Toast.LENGTH_SHORT).show();                    }
                },
                new WorkoutPlanAdapter.OnDeleteClickListener() {
                    @Override
                    public void onDeleteClick(int position, WorkoutPlan workoutPlan) {
                        int deletedRows = databaseHelper.deleteWorkoutPlan(workoutPlan.getWpId());
                        if (deletedRows > 0) {
                            workoutPlans.remove(position);
                            workoutPlanAdapter.notifyItemRemoved(position);
                            Toast.makeText(WorkoutPlanActivity.this, "Workout " + workoutPlan.getWpName() + " deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(WorkoutPlanActivity.this, "Failed to delete workout " + workoutPlan.getWpName(), Toast.LENGTH_SHORT).show();
                        }                    }
                });

        recyclerView.setAdapter(workoutPlanAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}