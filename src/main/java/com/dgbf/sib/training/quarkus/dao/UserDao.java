package com.dgbf.sib.training.quarkus.dao;

import com.dgbf.sib.training.quarkus.exception.user.UserNotCreateException;
import com.dgbf.sib.training.quarkus.model.Task;
import com.dgbf.sib.training.quarkus.exception.user.UserExistException;
import com.dgbf.sib.training.quarkus.exception.user.UserNotExistException;
import com.dgbf.sib.training.quarkus.model.User;

import java.util.List;

public interface UserDao extends AbstractDao {

    List<User> findAll();

    User findById(int id) throws UserNotExistException;

    User findByLogin(String login);

    User save(User user) throws UserNotCreateException, UserExistException;

    Boolean delete(User user) throws UserNotExistException;

    User update(User user) throws UserNotExistException;

    User doLogin(String login, String pwd) throws UserNotExistException;

    Boolean assignateTasksToUser(List<Task> lstTasks, User user);
}
