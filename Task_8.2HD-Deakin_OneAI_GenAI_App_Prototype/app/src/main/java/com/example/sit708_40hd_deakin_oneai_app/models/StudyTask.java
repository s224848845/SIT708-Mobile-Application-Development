package com.example.sit708_40hd_deakin_oneai_app.models;

/**
 * Simple model class representing a study or assignment task.
 */
public class StudyTask {

    private String unitName;
    private String taskName;
    private String dueDate;
    private String priority;

    public StudyTask(String unitName, String taskName, String dueDate, String priority) {
        this.unitName = unitName;
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}