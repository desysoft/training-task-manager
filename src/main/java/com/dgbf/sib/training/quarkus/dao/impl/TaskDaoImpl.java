package com.dgbf.sib.training.quarkus.dao.impl;

import com.dgbf.sib.training.quarkus.exception.task.TaskCodeExistException;
import com.dgbf.sib.training.quarkus.exception.task.TaskExistException;
import com.dgbf.sib.training.quarkus.exception.task.TaskNotExistException;
import com.dgbf.sib.training.quarkus.model.Task;
import com.dgbf.sib.training.quarkus.dao.TaskDao;
import com.dgbf.sib.training.quarkus.model.Activity;
import com.dgbf.sib.training.quarkus.model.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TaskDaoImpl implements TaskDao {

    public static List<Task> tasks = new ArrayList<>();
    static {
        User oneUser = new User(1,"Labité", "Désiré", "user","****");
        tasks.add(new Task(1, "T001", "fonctionnalité d'ajout", "Ajout de tache", Float.valueOf("1.5"), oneUser));
        tasks.add(new Task(2, "T002", "fonctionnalité de modification", "Modification de tache", Float.valueOf("2.5"), oneUser));
        tasks.add(new Task(3, "T003", "fonctionnalité de suppression", "Suppression de tache", Float.valueOf("1"), oneUser));
    }


    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public Task findById(int id) throws TaskNotExistException {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        throw new TaskNotExistException("La tache d'Id : "+id+ " n'existe pas");
    }

    @Override
    public Task save(Task task) throws TaskCodeExistException, TaskExistException{
        Task oTask = this.findById(task.getId());
        if (oTask == null) {
            oTask = this.findByCode(task.getCode());
            if(oTask==null){
                task.setId(this.getNextId());
                tasks.add(task);
                return task;
            }else{
                throw new TaskCodeExistException("Ce code " +task.getCode()+" existe déja");
            }

        } else {
            throw new TaskExistException("Cette tache avec pour id : "+ task.getId()+" existe déja");
        }
    }

    @Override
    public List<Task> findAllByUser(User user) {
        List<Task> lst = new ArrayList<>();
        for (Task oTask : tasks) {
            if (oTask.getUser().getId() == user.getId()) {
                lst.add(oTask);
            }
        }
        return lst;
    }

    @Override
    public Boolean addActivityInTask(Task OTask, Activity activity) {
        return OTask.getLstActivities().add(activity);
    }

    @Override
    public int getNextId() {
        int nextId = tasks.size() + 1;
        if (tasks.isEmpty()) {
            return 1;
        } else {

            Boolean isExist = false;
            do {
                isExist = false;
                for (Task task : tasks) {
                    if (task.getId() == nextId) {
                        isExist = true;
                        nextId++;
                        break;
                    }
                }

            } while (isExist == true);
        }
        return nextId;
    }

    @Override
    public Task findByCode(String code){
        for (Task oTask : tasks){
            if(oTask.getCode().equalsIgnoreCase(code)){
                return oTask;
            }
        }
        return null;
    }
}
