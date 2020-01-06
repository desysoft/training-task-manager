package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.exception.operation.OperationNotExistException;
import ci.gouv.dgbf.sib.taskmanager.exception.project.ProjectNotExistException;
import ci.gouv.dgbf.sib.taskmanager.model.*;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class ProjectDao extends AbstractDao implements PanacheRepositoryBase<Project, String> {

    @Inject
    VersionProjectDao OVersionProjectDao;

    @Inject
    OperationDao OOperationDao;

    @Inject
    TaskDao OTaskDao;

    @Inject
    ProjectPersonDao OProjectPersonDao;

    @Inject
    EntityManager em;

    public Optional<Project> findByIdCustom(String id) {
        return getProjectWithRateComplteProject(find("id = ?1 AND status = ?2 ", id, ParametersConfig.status_enable).list());
    }

    public List<Project> findAllProject() {
        return find("status = ?1", ParametersConfig.status_enable).list();
    }


    public List<Project> findAllProject(String search_value) {
        return find("(name LIKE :search_value OR description LIKE :search_value) AND status = :status", Parameters.with("search_value", "%" + search_value + "%").and("status", ParametersConfig.status_enable)).stream().peek(project -> project.setProjectCompletionRate()).collect(Collectors.toList());
    }

    public List<Task> findAllTasksProject(String id_project) {
        List<Task> lst = em.createQuery("SELECT t FROM Task t WHERE t.OProjectPerson.OProject.id = ?1 AND t.status = ?2")
                .setParameter(1, id_project)
                .setParameter(2, ParametersConfig.status_enable)
                .getResultList();
        for (Task t : lst) {
            em.refresh(t);
        }
        return lst;
    }

    public List<Project> findAllPersonProjects(String id_person) {
        List<ProjectPerson> lstProjectPerson =  OProjectPersonDao.findAllProjectPerson("",id_person,"");
        return lstProjectPerson.stream().map(projectPerson -> projectPerson.OProject).collect(Collectors.toList());
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
        try {
            Project oProject = findById(id);
            if (oProject == null) {
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.projectNotFoundMessage);
                return false;
            }
            List<ProjectPerson> lstProjectPerson = OProjectPersonDao.findAllProjectPerson(oProject.id, "", "");
            lstProjectPerson.stream().forEach(projectPerson -> OProjectPersonDao.deleteProjectPerson(projectPerson));
            oProject.status = ParametersConfig.status_delete;
            this.persist(oProject);
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_DELETE);
            return true;
        }catch (Exception e){
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_DELETE);
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteProject(Project project) {
        try {
            Optional<Project> oProject = this.findByIdCustom(project.id);
            if (!oProject.isPresent()) {
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.projectNotFoundMessage);
                return false;
            }
            List<ProjectPerson> lstProjectPerson = oProject.get().lstProjectPerson;
            lstProjectPerson.stream().forEach(projectPerson -> OProjectPersonDao.deleteProjectPerson(projectPerson));
            project.status = ParametersConfig.status_delete;
            this.persist(project);
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_DELETE);
            return true;
        }catch (Exception e){
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_DELETE);
            e.printStackTrace();
            return false;
        }
    }


    public Boolean assignateTaskToPersonInProjet(Task task, Person person, Project project) {
        try {
            System.out.println("assignateTaskToPersonInProjet task ==== "+task);

            Task oTask = OTaskDao.findByIdCustom(task.id).orElse(null);
            System.out.println("oTask.OProjectPerson ==== "+oTask);
            System.out.println("oTask.OProjectPerson ==== "+oTask.OProjectPerson);
            if(oTask==null){
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.taskTableNameInApplication+" "+ParametersConfig.genericNotFoundMessage);
                return false;
            }
            ProjectPerson oProjectPerson = OProjectPersonDao.findAllProjectPerson(project.id, person.id,"").stream().findFirst().orElse(null);
            if(oProjectPerson==null){
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.GENERIC_MESSAGE_PROCESS_FAILED);
                return false;
            }
            System.out.println("assignateTaskToPersonInProjet oProjectPerson ==== "+oProjectPerson);
            if(oTask.OProjectPerson!=null && oTask.OProjectPerson.id == oProjectPerson.id){
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.FAILED_TASK_ALREADY_LINKED_TO_PERSON);
                return false;
            }
            OTaskDao.getOVersionTaskDao().addVersionTask(oTask, OTaskDao.getOOperationDao().findByIdCustom(ParametersConfig.id_assignateTaskOperation));
            oTask.OProjectPerson = oProjectPerson;
            if(oTask.p_key_project_id == null || oTask.p_key_project_id.equals("")) oTask.p_key_project_id = oProjectPerson.OProject.id;
            oTask.persist();
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_LINKED);
            return true;
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_LINKED);
            e.printStackTrace();
            return false;
        }
    }

    public Boolean addTasksInProjet(List<Task> lstTasks, Project project, String updatedBy) {
        try {
            Project oProject = this.findByIdCustom(project.id).orElse(null);
            if(oProject==null){
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.GENERIC_MESSAGE_PROCESS_FAILED);
                return false;
            }
            lstTasks.stream().forEach(task -> {
                Task oTask = OTaskDao.findByIdCustom(task.id).orElse(null);
                if(oTask!=null){
                    oTask.p_key_project_id = project.id;
                    oTask.updatedBy = updatedBy;
                    oTask.persist();
                }
            });
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_LINKED);
            return true;
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_LINKED);
            e.printStackTrace();
            return false;
        }
    }



//    public Boolean addTaskInProjet(String id_task, String id_project) {
//        try {
//            List<ProjectPerson> lstProjectPerson = OProjectPersonDao.findAllProjectPerson(id_project, "", "");
//            if (lstProjectPerson.size() > 0) {
//                Task oTask = OTaskDao.findByIdCustom(id_task).orElse(null);
//                if (oTask != null) {
//                    oTask.OProjectPerson = lstProjectPerson.get(0);
//                    OTaskDao.persist(oTask);
//                    return OTaskDao.isPersistent(oTask);
//                } else return false;
//            } else return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public void addTasksInProjet(List<Task> lstTasks, String id_project) throws ProjectNotExistException {
//        try {
//            List<ProjectPerson> lstProjectPerson = OProjectPersonDao.findAllProjectPerson(id_project, "", "");
//            lstTasks.stream().forEach(task -> {
//                Task oTask = OTaskDao.findByIdCustom(task.id).orElse(null);
//                if(oTask!=null){
//                    oTask.OProjectPerson = lstProjectPerson.get(0);
//                    oTask.persist();
//                }
//            });
//
////            for (Task t : lstTasks) {
////                Task oTask = OTaskDao.findById(t.id);
////                if (oTask != null) {
////                    t.OProjectPerson = lstProjectPerson.get(0);
////                    OTaskDao.persist(t);
////                }
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public Boolean designateLeadProject(String id_project, Person person) throws ProjectNotExistException, OperationNotExistException {
        try {

            Project oProject = this.findByIdCustom(id_project).orElse(null);
            if (oProject == null) throw new ProjectNotExistException(ParametersConfig.projectNotFoundMessage);
            Operation oOperation = OOperationDao.findByIdCustom("1");
            if (oOperation == null)
                throw new OperationNotExistException("Action sur projet impossible car non définit dans la base de données");
            OVersionProjectDao.addVersionProject(oProject, oOperation);
            oProject.OPerson = person;
            this.persist(oProject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Project> getProjectWithRateComplteProject(List<Project> lstProjects) {
        return lstProjects.stream().peek(project -> project.setProjectCompletionRate()).findFirst();
    }

    public TaskDao getOTaskDao() {
        return OTaskDao;
    }

    public ProjectPersonDao getOProjectPersonDao() {
        return OProjectPersonDao;
    }
}
