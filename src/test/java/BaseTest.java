import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.*;

public class BaseTest {

    public static final String BASE_URL = "https://api.nasa.gov";
    public static final String MARS_ROVERS_PATH = "/mars-photos/api/v1/rovers";
    public static final String PHOTOS_PATH= "/photos";
    public static final String CURIOSITY_PATH = "/curiosity";
    public static final String MARS_ROVERS_CURIOSITY_PHOTOS_PATH = BASE_URL.concat(MARS_ROVERS_PATH).concat(CURIOSITY_PATH).concat(PHOTOS_PATH);
    private static String API_KEY;
    private static RequestSpecification nasaRequestSpec;
    private static RequestSpecification unauthorizedNasaRequestSpec;
    private static ResponseSpecification nasaResponseSpec;

    @BeforeClass(alwaysRun=true)
    public void setUp(){
        API_KEY = System.getenv("NASA_API_KEY");
        if (StringUtils.isEmpty(API_KEY)) throw new RuntimeException("Please configure a valid NASA_API_KEY in your OS PATH to proceed");
        nasaRequestSpec = new RequestSpecBuilder().
                setBaseUri(BASE_URL).
                addQueryParam("api_key", API_KEY).
                build();
        nasaResponseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
        unauthorizedNasaRequestSpec = new RequestSpecBuilder().
                setBaseUri(BASE_URL).
                addQueryParam("api_key", "01234NotAPIKEY").
                build();
    }

    public static RequestSpecification getNasaRequestSpec() {
        return nasaRequestSpec;
    }

    public static RequestSpecification getUnauthorizedNasaRequestSpec() {
        return unauthorizedNasaRequestSpec;
    }

    public static ResponseSpecification getNasaResponseSpec() {
        return nasaResponseSpec;
    }
}
