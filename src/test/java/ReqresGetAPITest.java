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

public class ReqresGetAPITest {
    final Logger LOGGER = LogManager.getLogger(ReqresGetAPITest.class.getSimpleName());

    @Before
    public void beforeMethod() {

        RestAssured.baseURI = "https://reqres.in";
        PropertyConfigurator.configure("log4j.properties");
    }

    @Test
    public void getAPITest() throws Exception {

        LOGGER.info(" <------------- Start --------------->");

        RequestSpecification request = RestAssured.given();

        Response response = request
                .param("page", "1")
                .get("/api/users");

        LOGGER.info("Response Body :" + response.body().asPrettyString());
        JsonPath jp = response.jsonPath();
        Assert.assertEquals("george.bluth@reqres.in", jp.getString("data[0].email"));
        Assert.assertEquals("1", jp.getString("page"));
        Assert.assertEquals("6", jp.getString("per_page"));
        Assert.assertEquals("12", jp.getString("total"));
        Assert.assertEquals("2", jp.getString("total_pages"));

     //  LOGGER.info(" value of id in data[0] is "+ jp.get("data[0].id"));

        LOGGER.info("  Verify: 1.The value of id, 2. Domain name of the user email contains @reqres.in,  3. first_name is not null, 4. last_name is not null" +
                " 5. Avatar is present");

        for(int i = 0; i<6; i++){

            int id = i +1;
            LOGGER.debug(" value of id "+jp.getInt("data["+i+"].id"));

            Assert.assertEquals(i+1,jp.getInt("data["+i+"].id"));
            Assert.assertTrue(jp.getString("data["+i+"].email").contains("@reqres.in"));
            Assert.assertTrue(!jp.getString("data[" + i + "].first_name").equals(null));
            Assert.assertTrue(!jp.getString("data[" + i + "].last_name").equals(null));
            Assert.assertEquals(jp.getString("data[" + i + "].avatar"),ReqresConstants.AVATAR + id + "-image.jpg");
        }


        Assert.assertEquals(jp.getString("support.url"),ReqresConstants.SUPPORT_URL);
        Assert.assertEquals(jp.getString("support.text"),ReqresConstants.SUPPORT_TEXT);


        LOGGER.info(" <------------- End --------------->");

    }

}


