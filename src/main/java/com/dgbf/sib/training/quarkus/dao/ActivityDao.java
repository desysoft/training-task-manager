package com.dgbf.sib.training.quarkus.dao;

import com.dgbf.sib.training.quarkus.model.Activity;
import com.dgbf.sib.training.quarkus.model.Task;
import com.dgbf.sib.training.quarkus.model.User;

import java.util.List;

public interface ActivityDao extends AbstractDao {

    List<Activity> findAll();

    Activity findById(int id);

    Activity save(Activity Activity);

    Activity findByCode(String code);

    List<Activity> findAllByTask(int id_tache);
}
