package ci.gouv.dgbf.sib.taskmanager.exception.project;

public class ProjectNotExistException extends RuntimeException {
    public ProjectNotExistException(String message) {
        super(message);
    }
}
