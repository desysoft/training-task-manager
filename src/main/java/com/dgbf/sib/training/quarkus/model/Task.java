package com.dgbf.sib.training.quarkus.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "t_task")
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_task_id_seq")
    private Integer id;
    private String code;
    private String name;
    private String description;
    private float nbrEstimateHours;
    private String status;

    @ManyToOne
    private User OUser;
    private LocalDate dt_created;
    private LocalDate dt_updated;

    @OneToMany(mappedBy = "OTask")
    private List<Activity> lstActivities;

    public Task() {
    }

    public Task(int id, String code, String name, String description, float nbrEstimateHours) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.nbrEstimateHours = nbrEstimateHours;
        lstActivities = new ArrayList<>();
    }

    public Task(int id, String code, String name, String description, float nbrEstimateHours, User OUser) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.nbrEstimateHours = nbrEstimateHours;
        this.OUser = OUser;
        lstActivities = new ArrayList<>();
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

    public float getnbrEstimateHours() {
        return nbrEstimateHours;
    }

    public void setnbrEstimateHours(float nbrEstimateHours) {
        this.nbrEstimateHours = nbrEstimateHours;
    }

    public User getOUser() {
        return OUser;
    }

    public void setOUser(User OUser) {
        this.OUser = OUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDt_created() {
        return dt_created;
    }

    public void setDt_created(LocalDate dt_created) {
        this.dt_created = dt_created;
    }

    public LocalDate getDt_updated() {
        return dt_updated;
    }

    public void setDt_updated(LocalDate dt_updated) {
        this.dt_updated = dt_updated;
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
