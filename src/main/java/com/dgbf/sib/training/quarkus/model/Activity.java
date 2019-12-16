package com.dgbf.sib.training.quarkus.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_activity_id_seq")
    private Integer id;
    private String code;
    private String label;
    private String description;
    private LocalDate start_date;
    private LocalDate end_date;
    private String status;
    private LocalDate dt_created;
    private LocalDate dt_updated;

    @ManyToOne
    private Task OTask;

    public Activity() {
    }


    public Activity(int id, String code, String label, String description, LocalDate start_date, LocalDate end_date, Task oTask) {
        this.id = id;
        this.code = code;
        this.label = label;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.OTask = oTask;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public String getStatut() {
        return status;
    }

    public void setStatut(String statut) {
        this.status = statut;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public Task getOTask() {
        return OTask;
    }

    public void setOTask(Task OTask) {
        this.OTask = OTask;
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

    @Override
    public String toString(){
        return "User{"+
                ", id="+this.id+
                ", label="+this.label+
                " }";
    }
}
