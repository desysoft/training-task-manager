package ci.gouv.dgbf.sib.taskmanager.resource.project;

import ci.gouv.dgbf.sib.taskmanager.dao.ProjectDao;
import ci.gouv.dgbf.sib.taskmanager.model.Person;
import ci.gouv.dgbf.sib.taskmanager.model.Project;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.project.ProjectPersonTasks;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.project.ProjectPersons;
import com.sun.org.apache.bcel.internal.generic.RET;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

@Path("project")
@Produces(MediaType.APPLICATION_JSON)
public class ProjectResource {

    @Inject
    ProjectDao OProjectDao;

    @GET
    @Path("find")
    public List<Project> listeDesProjets() {
        List<Project> lstProjects = OProjectDao.findAllProject();
        if (lstProjects.isEmpty()) throw new WebApplicationException("Liste vide", Response.noContent().build());
        return lstProjects;
    }

    @GET
    @Path("find/{id}")
    public Project trouverProjetParSonId(@PathParam("id") String id) {
        Project oProject = OProjectDao.findByIdCustom(id).orElse(null);
        if (oProject == null) throw new WebApplicationException("Projet introuvable", Response.noContent().build());
        return oProject;
    }

    @GET
    @Path("search/{search_value}")
    public List<Project> rechercherProjetParMotCle(@PathParam("search_value") String search_value) {
        List<Project> lstProjects = OProjectDao.findAllProject(search_value);
        if (lstProjects.isEmpty())
            throw new WebApplicationException("Aucune tâche correspondant au critère de recherche", Response.noContent().build());
        return lstProjects;
    }

    @GET
    @Path("projetTasks/{id}")
    public List<Task> obtenirLesTachesDuProjet(@PathParam("id") String id_project) {
        return OProjectDao.findAllTasksProject(id_project);
    }

    @GET
    @Path("personProject/{id_person}")
    public List<Project> obtenirLesProjetsDeLaPersonne(@PathParam("id_person") String id_person) {
        return OProjectDao.findAllPersonProjects(id_person);
    }

    @POST
    @Path("add")
    public Response ajouterUnProjet(Project project) {
        try {
            if (OProjectDao.addProject(project)) {
                URI oUri = UriBuilder.fromPath("project/find").path("{id}").build(project.id);
                return Response.created(oUri).build();
            } else return Response.notModified().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("update/{id}/operation/{id_operation}")
    public Response modifierUnProjet(@PathParam("id") String id_project, @PathParam("id_operation") String id_operation, Project project) {
        try {
            if (OProjectDao.updateProject(id_project, id_operation, project)) {
                URI oUri = UriBuilder.fromPath("project/find").path("{id}").build(id_project);
                return Response.ok().contentLocation(oUri).build();
            } else throw new WebApplicationException("La modification a échouée", Response.notModified().build());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.notModified().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("assignateTask/{updatedBy}")

    public Response assignerTacheAUnePersonneSurUnProjet(ProjectPersonTasks projectPersonTasks, String updatedBy) {
        try {
            projectPersonTasks.getLstTasks().stream().forEach(task -> {
                OProjectDao.assignateTaskToPersonInProjet(task, projectPersonTasks.getOPerson(), projectPersonTasks.getOProject());
                System.out.println("OProjectDao DetailMessage === "+OProjectDao.getDetailMessage());
            });
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @POST
    @Path("assignatetoproject/{createdBy}")
    @Transactional
    public Response assignerDesPersonnesAUnProjet(ProjectPersons projectPersons, @PathParam("createdBy") String createdBy) {
        try {
            projectPersons.getLstPerson().stream().forEach(person -> {
                OProjectDao.getOProjectPersonDao().addProjectPerson(projectPersons.getOProject().id, person.id, createdBy);
            });
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().tag(OProjectDao.getDetailMessage()).build();
        }
    }

    @PUT
//    @Path("addTheseTasksToProject/{id_project}")
    @Path("addTheseTasksToProject/{updatedBy}")
    public Response ajouterDesTachesAUnProjet(ProjectPersonTasks projectPersonTasks, @PathParam("updatedBy") String updatedBy) {
        try {
            OProjectDao.addTasksInProjet(projectPersonTasks.getLstTasks(), projectPersonTasks.getOProject(), updatedBy);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().tag(OProjectDao.getDetailMessage()).build();
        }
    }

    @PUT
    @Path("designate/{id_project}")
    public Response designerResponsableProjet(@PathParam("id_project") String id_project, Person person) {
        try {
            if (OProjectDao.designateLeadProject(id_project, person)) {
                URI oUri = UriBuilder.fromPath("project/find").path("{id}").build(id_project);
                return Response.ok().contentLocation(oUri).build();
            } else return Response.notModified().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("delete/{id}")
    public Response supprimerUnProjet(@PathParam("id") String id_project) {
        try {
            if (OProjectDao.deleteProject(id_project)) {
                return Response.ok().build();
            } else {
                return Response.notModified().build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.notModified().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
