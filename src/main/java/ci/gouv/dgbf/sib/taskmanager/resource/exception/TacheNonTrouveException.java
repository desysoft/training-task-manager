package ci.gouv.dgbf.sib.taskmanager.resource.exception;

//public class TacheNonTrouveException extends RuntimeException {
//    public TacheNonTrouveException(String s) {
//        super(s);
//    }
//}

import javax.ws.rs.WebApplicationException;

public class TacheNonTrouveException extends WebApplicationException {
    public TacheNonTrouveException(String s) {
        super(s);
    }
}
