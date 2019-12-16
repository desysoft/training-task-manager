package com.dgbf.sib.training.quarkus.dao;

import com.dgbf.sib.training.quarkus.exception.task.TaskCodeExistException;
import com.dgbf.sib.training.quarkus.exception.task.TaskExistException;
import com.dgbf.sib.training.quarkus.exception.task.TaskNotExistException;
import com.dgbf.sib.training.quarkus.model.Task;
import com.dgbf.sib.training.quarkus.model.Activity;
import com.dgbf.sib.training.quarkus.model.User;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import sun.plugin2.util.ParameterNames;

import java.util.List;

public class TaskDao extends PanacheEntity {

}
