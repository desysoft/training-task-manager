package com.dgbf.sib.training.quarkus.model;

import java.util.List;

public class Task {


    private int id;
    private String code;
    private String name;
    private String description;
    private float NbrEstimateHours;
    private User user;

    private List<Activity> lstActivities;

    public Task() {
    }

    public Task(int id, String code, String name, String description, float NbrEstimateHours, User user) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.NbrEstimateHours = NbrEstimateHours;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getNbrEstimateHours() {
        return NbrEstimateHours;
    }

    public void setNbrEstimateHours(float NbrEstimateHours) {
        this.NbrEstimateHours = NbrEstimateHours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Activity> getLstActivities() {
        return lstActivities;
    }

    public void setLstActivities(List<Activity> lstActivities) {
        this.lstActivities = lstActivities;
    }

    @Override
    public String toString(){
        return "Task {"+
                " id = "+this.id+
                ", name = "+this.name+
                " }";
    }
}
