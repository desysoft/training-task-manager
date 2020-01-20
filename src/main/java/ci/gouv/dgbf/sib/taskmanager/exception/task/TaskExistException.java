package ci.gouv.dgbf.sib.taskmanager.exception.task;

public class TaskExistException extends RuntimeException {
    public TaskExistException(String message) {
        super(message);
    }
}
