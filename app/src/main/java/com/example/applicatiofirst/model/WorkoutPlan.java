package com.example.applicatiofirst.model;

public class WorkoutPlan {
    private int wpId;
    private String wpName;
    private String timeGoal;
    private String type;
    private int userId;

    public WorkoutPlan(int wpId, String wpName, String timeGoal, String type, int userId) {
        this.wpId = wpId;
        this.wpName = wpName;
        this.timeGoal = timeGoal;
        this.type = type;
        this.userId = userId;
    }

    public int getWpId() {
        return wpId;
    }

    public String getWpName() {
        return wpName;
    }

    public String getTimeGoal() {
        return timeGoal;
    }

    public String getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }
}
