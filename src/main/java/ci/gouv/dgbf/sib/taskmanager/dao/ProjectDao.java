package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.exception.project.ProjectNotExistException;
import ci.gouv.dgbf.sib.taskmanager.exception.task.TaskNotExistException;
import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.Operation;
import ci.gouv.dgbf.sib.taskmanager.model.Project;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class ProjectDao implements PanacheRepositoryBase<Project, String> {

    @Inject
    VersionProjectDao OVersionProjectDao;

    @Inject
    OperationDao OOperationDao;

    @Inject
    TaskDao OTaskDao;


    public Project findById(String id) {
        return find("id = ?1 AND status = ?2 ", id, ParametersConfig.status_enable).firstResult();
    }

    public List<Project> findAllProject() {
        return find("status = ?1", ParametersConfig.status_enable).list();
    }

    public List<Project> findAllProject(String search_value) {
        return find("(name LIKE :search_value OR description LIKE :search_value) AND status = :status", Parameters.with("search_value", "%"+search_value+"%").and("status", ParametersConfig.status_enable)).list();
    }

    public boolean addProject(Project project) {
        try {
            this.persist(project);
            return this.isPersistent(project);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProject(String id_project, String id_operation, Project project) throws ProjectNotExistException {
        Project oProject = this.findById(id_project);
        if (oProject != null) {
            Operation oOperation = OOperationDao.findById(id_operation);
            if (oOperation != null) {
                OVersionProjectDao.addVersionProject(oProject, oOperation);
                if (project.name != null && !project.name.equals(""))
                    oProject.name = project.name;
                if (project.description != null && !project.description.equals(""))
                    oProject.description = project.description;
                if (project.dt_startProject != null && !project.dt_startProject.equals(""))
                    oProject.dt_startProject = project.dt_startProject;
                if (project.dt_endProject != null && !project.dt_endProject.equals(""))
                    oProject.dt_endProject = project.dt_endProject;
                return this.isPersistent(oProject);
            } else return false;
        } else return false;
    }

    public Boolean deleteProject(String id) throws ProjectNotExistException {
        Project oProject = findById(id);
        List<Task> lstTask = oProject.lstTasks;
        if (oProject != null) {
            oProject.status = ParametersConfig.status_delete;
            for (Task t : lstTask) {
                if (!OTaskDao.deleteTask(t))
                    return false;
                break;
            }
            return isPersistent(oProject);
        } else return false;
    }

    public Boolean deleteProject(Project project) {
        List<Task> lstTask = project.lstTasks;
        project.status = ParametersConfig.status_delete;
        for (Task t : lstTask) {
            if (!OTaskDao.deleteTask(t))
                return false;
            break;
        }
        return isPersistent(project);
    }

    public Boolean addTaskInProjet(String id_task, String id_project){
        Project oProject = findById(id_project);
        if(oProject!=null){
            Task oTask = OTaskDao.findById(id_task);
            if(oTask!=null){
                oTask.OProject = oProject;
                OTaskDao.persist(oTask);
                return OTaskDao.isPersistent(oTask);
            }else return false;
        }else return false;
    }

    public void addTasksInProjet(List<Task> lstTasks, String id_project) throws ProjectNotExistException{
        Project oProject = this.findById(id_project);
        if(oProject==null) throw new ProjectNotExistException("Projet introuvable");
        for(Task t : lstTasks){
            Task oTask = OTaskDao.findById(t.id);
            if(oTask!=null){
                t.OProject  = oProject;
                OTaskDao.persist(t);
            }
        }
    }

    public TaskDao getOTaskDao() {
        return OTaskDao;
    }
}
