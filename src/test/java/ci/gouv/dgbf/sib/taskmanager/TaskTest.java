package ci.gouv.dgbf.sib.taskmanager;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TaskTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/task")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}
