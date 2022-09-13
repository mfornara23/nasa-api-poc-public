import entities.Photo;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * Set of tests created using the POISED heuristic
 */
public class OtherTests extends BaseTest {

    @Test(groups = {"ALL", "POISED"})
    @Description("Auth with wrong API KEY")
    public void authTest() {
        given().
                spec(getUnauthorizedNasaRequestSpec()).
                when().
                get(MARS_ROVERS_CURIOSITY_PHOTOS_PATH).
                then().
                assertThat().
                statusCode(403).
                and().
                body("error.code", equalTo("API_KEY_INVALID"));
    }

    @Test(groups = {"ALL", "POISED"}, dataProvider = "sol-provider")
    @Description("Testing with different Sol values")
    public void differentSolValues(String sol, int amountPhotos) {
        List<Photo> photos = given().
                spec(getNasaRequestSpec()).
                queryParam("sol", sol).
                when().
                get(MARS_ROVERS_CURIOSITY_PHOTOS_PATH).
                then().
                spec(getNasaResponseSpec()).
                extract().
                body().
                jsonPath().getList("photos", Photo.class);

        Assert.assertEquals(photos.size(), amountPhotos);
    }

    @Test(groups = {"ALL", "POISED"})
    @Description("Dummy injection on earth_date param")
    public void injectionOnEarthDate() {
        given().
                spec(getNasaRequestSpec()).
                queryParam("earth_date", "<script>alert(\\\"test1\\\")</script>").
                when().
                get(MARS_ROVERS_CURIOSITY_PHOTOS_PATH).
                then().
                assertThat().
                statusCode(500);

    }


    @DataProvider(name = "sol-provider")
    public Object[][] solValues(){
        return new Object[][] {{"100",400}, {"-100",0},{"0",3702},{"",0},{"aaa",0}};
    }
}
