import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PostAPITest {
    final Logger LOGGER = LogManager.getLogger(PostAPITest.class.getSimpleName());

    @Before
    public void postAPITest() {

        LOGGER.debug("Inside the beforeMethod()");

        RestAssured.baseURI = "https://reqres.in";
        PropertyConfigurator.configure("log4j.properties");
    }

    @Test
    public void testMethod() {
        LOGGER.info(" <------------- Start --------------->");
        RequestSpecification request = RestAssured.given();

        String requestBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";


        Response response = request
                .body(requestBody)
                .post("/api/users");

        JsonPath jp = response.jsonPath();

        if(response.statusCode()!=201){
            LOGGER.debug("Response status code" +response.statusCode() );
        }
        Assert.assertEquals(201, response.statusCode());

        LOGGER.info("Response Body :" + response.body().asPrettyString());


        Assert.assertTrue("Id is empty", !jp.getString("id").equals(null));
        Assert.assertTrue("Created At is empty", !jp.getString("createdAt").equals(null));


        LOGGER.info(" <------------- End --------------->");


    }
}

