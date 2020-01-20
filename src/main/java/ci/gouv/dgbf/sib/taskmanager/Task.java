package ci.gouv.dgbf.sib.taskmanager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/welcome")
public class Task {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Welcome";
    }
}
