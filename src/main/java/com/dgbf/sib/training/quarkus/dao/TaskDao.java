package com.dgbf.sib.training.quarkus.dao;

import com.dgbf.sib.training.quarkus.exception.task.TaskCodeExistException;
import com.dgbf.sib.training.quarkus.exception.task.TaskExistException;
import com.dgbf.sib.training.quarkus.exception.task.TaskNotExistException;
import com.dgbf.sib.training.quarkus.model.Task;
import com.dgbf.sib.training.quarkus.model.Activity;
import com.dgbf.sib.training.quarkus.model.User;

import java.util.List;

public interface TaskDao {

    List<Task> findAll();

    Task findById(int id) throws TaskNotExistException;

    Task save(Task task) throws TaskCodeExistException, TaskExistException;

    List<Task> findAllByUser(User user);

    Boolean addActivityInTask(Task task, Activity activity);

    int getNextId();

    Task findByCode(String code);
}
