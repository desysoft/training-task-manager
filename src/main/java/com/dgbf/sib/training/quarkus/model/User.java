package com.dgbf.sib.training.quarkus.model;

import java.util.List;

public class User {

    private int id;
    private String nom;
    private String prenom;
    private String login;
    private String pwd;

    private List<Task> lstTasks;


    public User() {
    }


    public User(int id) {
        this.id = id;
    }

    public User(int id, String nom, String prenom, String login, String pwd) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.pwd = pwd;
    }

    public User(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
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

    @Override
    public String toString(){
        return "User{ "+
                "id = "+this.id+
                ", name = "+this.nom+
                " }";
    }


}
