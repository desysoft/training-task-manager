package com.dgbf.sib.training.quarkus.dao.impl;

import com.dgbf.sib.training.quarkus.dao.ActivityDao;
import com.dgbf.sib.training.quarkus.exception.activity.ActivityCodeExistException;
import com.dgbf.sib.training.quarkus.exception.activity.ActivityExistException;
import com.dgbf.sib.training.quarkus.exception.activity.ActivityNotExistException;
import com.dgbf.sib.training.quarkus.model.Activity;
import com.dgbf.sib.training.quarkus.model.Task;
import com.dgbf.sib.training.quarkus.model.User;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class ActivityDaoImpl implements ActivityDao {

    public static List<Activity> activities = new ArrayList<>();

    static {
        User oneUser = new User(1,"Labité", "Désiré", "user","****");
        Task oTask = new Task(1, "T001", "fonctionnalité d'ajout", "Ajout de tache", Float.valueOf("1.5"), oneUser);
        Task oTask2 = new Task(2, "T002", "fonctionnalité de modification", "Modification de tache", Float.valueOf("2"), oneUser);
        activities.add(new Activity(1, "A001","allumer machine","dsjh", LocalDate.now(),LocalDate.now(), oTask));
        activities.add(new Activity(2, "A002","Eteindre","Eteinde dsjh", LocalDate.now(),LocalDate.now(), oTask));
        activities.add(new Activity(3, "A003","Vendre","dsjh Vendre", LocalDate.now(),LocalDate.now(), oTask2));
    }

    @Override
    public List<Activity> findAll() {
        return activities;
    }

    @Override
    public Activity findById(int id) {
        for (Activity activity :  activities) {
            if (activity.getId() == id) {
                return activity;
            }
        }
        throw new ActivityNotExistException("L'activité d'Id : "+id+ " n'existe pas");
    }

    @Override
    public Activity save(Activity activity) {
        Activity oactivity = this.findById(activity.getId());
        if (oactivity == null) {
            oactivity = this.findByCode(activity.getCode());
            if(oactivity==null){
                activity.setId(this.getNextId());
                activities.add(activity);
                return activity;
            }else{
                throw new ActivityCodeExistException("Ce code " +activity.getCode()+" existe déja");
            }

        } else {
            throw new ActivityExistException("Cette tache avec pour id : "+ activity.getId()+" existe déja");
        }
    }

    @Override
    public Activity findByCode(String code) {
        for (Activity oActivity : activities){
            if(oActivity.getCode().equalsIgnoreCase(code)){
                return oActivity;
            }
        }
        return null;
    }

    @Override
    public List<Activity> findAllByTask(int id_tache) {
        List<Activity> lst = new ArrayList<>();
        for (Activity oActivity : activities) {
            if (oActivity.getOTask().getId() == id_tache) {
                lst.add(oActivity);
            }
        }
        return lst;
    }

    @Override
    public int getNextId() {
        int nextId = activities.size() + 1;
        if (activities.isEmpty()) {
            return 1;
        } else {

            Boolean isExist = false;
            do {
                isExist = false;
                for (Activity oActivity : activities) {
                    if (oActivity.getId() == nextId) {
                        isExist = true;
                        nextId++;
                        break;
                    }
                }

            } while (isExist == true);
        }
        return nextId;
    }
}
