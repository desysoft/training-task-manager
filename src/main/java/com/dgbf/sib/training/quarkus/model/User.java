package com.dgbf.sib.training.quarkus.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "t_user_id_seq")
    private Integer id;
    private String nom;
    private String prenom;
    private String login;
    private String pwd;
    private String status;

    private LocalDate dt_created;
    private LocalDate dt_updated;

    @OneToMany(mappedBy = "OUser")
    private List<Task> lstTasks;


    public User() {
    }


    public User(int id) {
        this.id = id;
        lstTasks = new ArrayList<>();
    }

    public User(int id, String nom, String prenom, String login, String pwd) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.pwd = pwd;
        lstTasks = new ArrayList<>();
    }

    public User(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        lstTasks = new ArrayList<>();
    }


    public User(String nom, String prenom, String login, String pwd) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<Task> getLstTasks() {
        return lstTasks;
    }

    public void setLstTasks(List<Task> lstTasks) {
        this.lstTasks = lstTasks;
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

    @Override
    public String toString(){
        return "User{ "+
                "id = "+this.id+
                ", name = "+this.nom+
                " }";
    }



}
