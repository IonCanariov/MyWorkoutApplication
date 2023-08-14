package com.example.applicatiofirst.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicatiofirst.R;
import com.example.applicatiofirst.util.DatabaseHelper;

public class CreateWorkoutActivity extends AppCompatActivity {

    private EditText wpNameEditText;
    private EditText workoutTypeEditText;
    private EditText timeGoalEditText;
    private Button createWorkoutButton;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        databaseHelper = new DatabaseHelper(this);

        wpNameEditText = findViewById(R.id.workoutNameEditText);
        workoutTypeEditText = findViewById(R.id.workoutTypeEditText);
        timeGoalEditText = findViewById(R.id.timeGoalEditText);
        createWorkoutButton = findViewById(R.id.createWorkoutButton);

        createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWorkout();
            }
        });
    }

    private void createWorkout() {
        String wpName = wpNameEditText.getText().toString();
        String workoutType = workoutTypeEditText.getText().toString();
        String timeGoal = timeGoalEditText.getText().toString();

        int userId = 1;

        long insertedRowId = databaseHelper.insertWorkoutPlan(wpName, timeGoal, workoutType, userId);
        if (insertedRowId != -1) {
            Toast.makeText(this, "Workout plan created successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to create workout plan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
