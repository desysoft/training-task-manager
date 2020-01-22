package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.exception.operation.OperationNotExistException;
import ci.gouv.dgbf.sib.taskmanager.exception.project.ProjectNotExistException;
import ci.gouv.dgbf.sib.taskmanager.model.*;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import com.sun.org.apache.bcel.internal.generic.RET;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import oracle.sql.NUMBER;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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


    public Optional<Project> findByIdCustom(String id) {

        return getProjectWithRateComplteProject(find("id = ?1 AND status = ?2 ", id, ParametersConfig.status_enable).list());
    }

    public List<Project> findAllProject() {
        return find("status <> ?1", ParametersConfig.status_delete).list().stream().peek(project -> project.setProjectCompletionRate()).collect(Collectors.toList());
    }

    public List<Project> findAllProject(String search_value) {
        return find("(name LIKE :search_value OR description LIKE :search_value) AND status <> :status", Parameters.with("search_value", "%" + search_value + "%").and("status", ParametersConfig.status_delete)).stream().peek(project -> project.setProjectCompletionRate()).collect(Collectors.toList());
    }

    public List<Task> findAllTasksProject(String id_project) {
        List<Task> lst = this.getEm().createQuery("SELECT t FROM Task t WHERE t.OProjectPerson.OProject.id = ?1 AND t.status = ?2")
                .setParameter(1, id_project)
                .setParameter(2, ParametersConfig.status_enable)
                .getResultList();
        for (Task t : lst) {
            this.getEm().refresh(t);
        }
        return lst;
    }

    public List<Person> findAllPersonOfProject(String id_project) {
        try {
            List<ProjectPerson> lstProjectPerson = OProjectPersonDao.findAllProjectPerson(id_project, "", "");
            return lstProjectPerson.stream().map(projectPerson -> projectPerson.OPerson).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Project checkExistProject(Project project) {
        Project oProject = this.findByIdCustom(project.id).orElse(null);
        if (oProject == null) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.genericNotFoundMessage);
            return null;
        }
        return oProject;
    }


    public Project addProject(Project project) {
        try {
            this.persist(project);
            return project;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Project updateProject(Project project) {
        try {
            Project oProject = this.checkExistProject(project);
            if (oProject == null) return null;
            Operation oOperation = OOperationDao.findByIdCustom(ParametersConfig.id_updateOperation);
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
                this.persist(oProject);
                this.setMessage(ParametersConfig.PROCESS_SUCCES);
                this.setDetailMessage(ParametersConfig.SUCCES_UPDATE);
                return oProject;
            }
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_UPDATE);
            e.printStackTrace();
        }
        return null;
    }


    public Boolean deleteProject(String id) throws ProjectNotExistException {
        try {
            Project oProject = findByIdCustom(id).orElse(null);
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
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_DELETE);
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteProject(Project project) {
        try {
            Project oProject = this.checkExistProject(project);
            if (oProject == null) return null;
            List<ProjectPerson> lstProjectPerson = oProject.lstProjectPerson;
            lstProjectPerson.stream().forEach(projectPerson -> OProjectPersonDao.deleteProjectPerson(projectPerson));
            project.status = ParametersConfig.status_delete;
            this.persist(project);
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_DELETE);
            return true;
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_DELETE);
            e.printStackTrace();
            return false;
        }
    }


    public Project assignateTaskToPersonInProjet(Task task, Person person, Project project) {
        try {
            System.out.println("assignateTaskToPersonInProjet task ==== " + task);

            Task oTask = OTaskDao.findByIdCustom(task.id).orElse(null);

            if (oTask == null) {
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.taskTableNameInApplication + " " + ParametersConfig.genericNotFoundMessage);
                return null;
            }
            System.out.println("oTask.OProjectPerson ==== " + oTask);
            System.out.println("oTask.OProjectPerson ==== " + ((oTask.OProjectPerson == null) ? "null" : oTask.OProjectPerson));
            ProjectPerson oProjectPerson = OProjectPersonDao.findAllProjectPerson(project.id, person.id, "").stream().findFirst().orElse(null);
            if (oProjectPerson == null) {
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.PROJECT_PERSON_NOT_EXIST);
                return null;
            }
            System.out.println("assignateTaskToPersonInProjet oProjectPerson ==== " + oProjectPerson);
            if (oTask.OProjectPerson != null && oTask.OProjectPerson.id == oProjectPerson.id) {
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.FAILED_TASK_ALREADY_LINKED_TO_PERSON);
                return null;
            }
            if(oTask.p_key_project_id == null || (oTask.p_key_project_id == oProjectPerson.OProject.id)){
                OTaskDao.getOVersionTaskDao().addVersionTask(oTask, OTaskDao.getOOperationDao().findByIdCustom(ParametersConfig.id_assignateTaskOperation));
                oTask.OProjectPerson = oProjectPerson;
                oTask.p_key_project_id = oProjectPerson.OProject.id;
                oTask.persist();
                this.setMessage(ParametersConfig.PROCESS_SUCCES);
                this.setDetailMessage(ParametersConfig.SUCCES_LINKED);
                return oProjectPerson.OProject;
            }
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_LINKED);
            e.printStackTrace();
            return null;
        }
        this.setMessage(ParametersConfig.PROCESS_FAILED);
        this.setDetailMessage(ParametersConfig.FAILED_LINKED);
        return null;
    }

    public Project addTasksInProjet(List<Task> lstTasks, Project project, String updatedBy) {
        try {
            Project oProject = this.findByIdCustom(project.id).orElse(null);
            if (oProject == null) {
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.GENERIC_MESSAGE_PROCESS_FAILED);
                return null;
            }
            final int[] succes = {0};
            lstTasks.stream().forEach(task -> {
                Task oTask = OTaskDao.findByIdCustom(task.id).orElse(null);
                if (oTask != null) {
                    if (oTask.p_key_project_id == null) {
                        if (oTask.OProjectPerson == null || (oTask.OProjectPerson != null && oTask.OProjectPerson.OProject.id == project.id)) {
                            OTaskDao.getOVersionTaskDao().addVersionTask(oTask,OOperationDao.findByIdCustom(ParametersConfig.id_assignateTaskToProject));
                            oTask.p_key_project_id = project.id;
                            oTask.updatedBy = updatedBy;
                            oTask.persist();
                            succes[0]++;
                        }
                    }
                }
            });
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(succes[0] + "/"+ lstTasks.size() + " " + ParametersConfig.SUCCES_LINKED);
            oProject.setProjectCompletionRate();
            return oProject;
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_LINKED);
            e.printStackTrace();
            return null;
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


    public Project designateLeadProject(String id_project, Person person) throws ProjectNotExistException, OperationNotExistException {
        try {

            Project oProject = this.findByIdCustom(id_project).orElse(null);
            if (oProject == null) throw new ProjectNotExistException(ParametersConfig.projectNotFoundMessage);
            Operation oOperation = OOperationDao.findByIdCustom(ParametersConfig.id_choseProjectLeadOperation);
            if (oOperation == null)
                throw new OperationNotExistException("Action sur projet impossible car non définit dans la base de données");
            OVersionProjectDao.addVersionProject(oProject, oOperation);
            oProject.OPerson = person;
            this.persist(oProject);
            return oProject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
