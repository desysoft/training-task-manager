package ci.gouv.dgbf.sib.taskmanager.exception.user;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
