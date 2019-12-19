package ci.gouv.dgbf.sib.taskmanager.exception.user;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }
}
