package com.example.applicatiofirst.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.applicatiofirst.model.User;
import com.example.applicatiofirst.model.WorkoutPlan;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "workout_app.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_WORKOUT_PLANS = "workout_plans";
    private static final String COLUMN_WP_ID = "wp_id";
    private static final String COLUMN_WP_NAME = "wp_name";
    private static final String COLUMN_TIME_GOAL = "time_goal";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_USER_ID_FK = "user_id_fk";

    // Create statement for users table
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT"
            + ")";

    // Create statement for workout plans table
    private static final String CREATE_WORKOUT_PLANS_TABLE = "CREATE TABLE " + TABLE_WORKOUT_PLANS + "("
            + COLUMN_WP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_WP_NAME + " TEXT,"
            + COLUMN_TIME_GOAL + " TEXT,"
            + COLUMN_TYPE + " TEXT,"
            + COLUMN_USER_ID_FK + " INTEGER,"
            + "FOREIGN KEY (" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_WORKOUT_PLANS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }

    public long insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long insertedRowId = db.insert(TABLE_USERS, null, values);
        db.close();
        return insertedRowId;
    }

    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(TABLE_USERS, null,
                COLUMN_USERNAME + "=?", new String[]{username},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            user = new User(userId, username, password);
            cursor.close();
        }

        db.close();
        return user;
    }
    public long insertWorkoutPlan(String wpName, String timeGoal, String type, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WP_NAME, wpName);
        values.put(COLUMN_TIME_GOAL, timeGoal);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_USER_ID_FK, userId);
        long insertedRowId = db.insert(TABLE_WORKOUT_PLANS, null, values);
        db.close();
        return insertedRowId;
    }

    public List<WorkoutPlan> getWorkoutPlansForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<WorkoutPlan> workoutPlans = new ArrayList<>();

        Cursor cursor = db.query(TABLE_WORKOUT_PLANS, null,
                COLUMN_USER_ID_FK + "=?", new String[]{String.valueOf(userId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int wpId = cursor.getInt(cursor.getColumnIndex(COLUMN_WP_ID));
                @SuppressLint("Range") String wpName = cursor.getString(cursor.getColumnIndex(COLUMN_WP_NAME));
                @SuppressLint("Range") String timeGoal = cursor.getString(cursor.getColumnIndex(COLUMN_TIME_GOAL));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                workoutPlans.add(new WorkoutPlan(wpId, wpName, timeGoal, type, userId));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return workoutPlans;
    }
    public int deleteWorkoutPlan(int workoutPlanId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_WORKOUT_PLANS, COLUMN_WP_ID + "=?", new String[]{String.valueOf(workoutPlanId)});
    }
    public void insertSampleData() {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_USERNAME, "Johny");
        userValues.put(COLUMN_PASSWORD, "12345");
        db.insert(TABLE_USERS, null, userValues);

        userValues.clear();
        userValues.put(COLUMN_USERNAME, "MUHAMED");
        userValues.put(COLUMN_PASSWORD, "NOTR");
        db.insert(TABLE_USERS, null, userValues);

        ContentValues workoutValues = new ContentValues();
        workoutValues.put(COLUMN_WP_NAME, "Work 1");
        workoutValues.put(COLUMN_TIME_GOAL, "30 minutes");
        workoutValues.put(COLUMN_TYPE, "Strength Training");
        workoutValues.put(COLUMN_USER_ID_FK, 1);
        db.insert(TABLE_WORKOUT_PLANS, null, workoutValues);

        workoutValues.clear();
        workoutValues.put(COLUMN_WP_NAME, "working out 2");
        workoutValues.put(COLUMN_TIME_GOAL, "45 minutes");
        workoutValues.put(COLUMN_TYPE, "Cardio");
        workoutValues.put(COLUMN_USER_ID_FK, 2);
        db.insert(TABLE_WORKOUT_PLANS, null, workoutValues);

        db.close();
    }
}
