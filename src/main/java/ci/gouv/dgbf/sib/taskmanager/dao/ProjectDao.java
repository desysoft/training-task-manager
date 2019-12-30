package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.exception.operation.OperationNotExistException;
import ci.gouv.dgbf.sib.taskmanager.exception.project.ProjectNotExistException;
import ci.gouv.dgbf.sib.taskmanager.exception.task.TaskNotExistException;
import ci.gouv.dgbf.sib.taskmanager.model.*;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import org.graalvm.collections.EconomicMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
@Transactional
public class ProjectDao implements PanacheRepositoryBase<Project, String> {

    @Inject
    VersionProjectDao OVersionProjectDao;

    @Inject
    OperationDao OOperationDao;

    @Inject
    TaskDao OTaskDao;

    @Inject
    EntityManager em;

    public Optional<Project> findByIdCustom(String id) {
        return getProjectWithRateComplteProject(find("id = ?1 AND status = ?2 ", id, ParametersConfig.status_enable).list());
    }

    public List<Project> findAllProject() {
        return find("status = ?1", ParametersConfig.status_enable).list();
    }


    public List<Project> findAllProject(String search_value) {
        return find("(name LIKE :search_value OR description LIKE :search_value) AND status = :status", Parameters.with("search_value", "%"+search_value+"%").and("status", ParametersConfig.status_enable)).stream().peek(project -> project.setProjectCompletionRate()).collect(Collectors.toList());
    }

    public List<Task> findAllTasksProject(String id_project){
        List<Task> lst = em.createQuery("SELECT t FROM Task t WHERE t.OProject.id = ?1 AND t.status = ?2")
                .setParameter(1, id_project)
                .setParameter(2, ParametersConfig.status_enable)
                .getResultList();
        for (Task t: lst) {
            em.refresh(t);
        }
        return lst;
    }

    public List<Project> findAllPersonProjects(String id_person){
        return find("OPerson.id = ?1 AND status = ?2", id_person, ParametersConfig.status_enable).list();
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

    public Boolean designateLeadProject(String id_project, Person person) throws ProjectNotExistException, OperationNotExistException{
        try{

            Project oProject = this.findByIdCustom(id_project).orElse(null);
            if(oProject==null) throw new ProjectNotExistException("Projet introuvable");
            Operation oOperation =OOperationDao.findByIdCustom("1");
            if(oOperation==null) throw new OperationNotExistException("Action sur projet impossible car non définit dans la base de données");
            OVersionProjectDao.addVersionProject(oProject, oOperation);
            oProject.OPerson = person;
            this.persist(oProject);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Project> getProjectWithRateComplteProject(List<Project> lstProjects){
        return lstProjects.stream().peek(project -> project.setProjectCompletionRate()).findFirst();
    }

    public TaskDao getOTaskDao() {
        return OTaskDao;
    }
}
