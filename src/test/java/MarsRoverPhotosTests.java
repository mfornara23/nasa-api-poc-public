import entities.Photo;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class MarsRoverPhotosTests extends BaseTest {

    private String roverName = "Curiosity";
    private int solAmount = 1000;
    private String date = "2015-05-30";

    @Test(groups = {"ALL", "CURIOSITY"})
    @Description("Retrieve the first 10 Mars photos made by \"Curiosity\" on 1000 Martian sol")
    public void marsPhotosTest01() {
        List<Photo> photos = getCuriosityPhotosBySol(1000);
        Assert.assertTrue(photos.size() >= 10, "Less than 10 photos were retrieved");

        for (Photo p : photos) {
            Assert.assertEquals(p.getRover().getName(), roverName, "Rover name doesn't match");
            Assert.assertEquals(p.getSol(), solAmount, "Sol number doesn't match");
        }
    }

    @Test(groups = {"ALL", "CURIOSITY"})
    @Description("Retrieve the first 10 Mars photos made by \"Curiosity\" on Earth date equal to 1000 Martian sol")
    public void marsPhotosTest02() {
        List<Photo> photos = getCuriosityPhotosByEarthDate(date);
        Assert.assertTrue(photos.size() >= 10, "Less than 10 photos were retrieved");

        for (Photo p : photos) {
            Assert.assertEquals(p.getRover().getName(), roverName, "Rover name doesn't match");
            Assert.assertEquals(p.getSol(), solAmount, "Sol number doesn't match");
        }
    }

    @Test(groups = {"ALL", "CURIOSITY"})
    @Description("Retrieve and compare the first 10 Mars photos made by \"Curiosity\" on 1000 sol and on Earth date equal to 1000 Martian sol")
    public void marsPhotosTest03() {
        List<Photo> photosBySol = getCuriosityPhotosBySol(solAmount);
        List<Photo> photosByDate = getCuriosityPhotosByEarthDate(date);
        Assert.assertTrue(photosBySol.size() >= 10 && photosByDate.size() >= 10);

        Iterator<Photo> itByDate = photosByDate.iterator();
        Iterator<Photo> itBySol = photosBySol.iterator();

        while (itByDate.hasNext() && itBySol.hasNext()) {
            Assert.assertEquals(itByDate.next().getId(), itBySol.next().getId(), "Photos don't match");
        }
    }

    @Test(groups = {"ALL", "CURIOSITY"})
    @Description("Validate that the amounts of pictures that each \"Curiosity\" camera took on 1000 Mars sol is not greater than 10 times the amount taken by other cameras on the same date.")
    public void marsPhotosTest04() {

        Map<String, Long> photosByCamera = getCuriosityPhotosBySol(solAmount).stream()
                .collect(groupingBy(p -> p.getCamera().getName(), counting()));

        for (Map.Entry<String, Long> p1 : photosByCamera.entrySet()) {
            for (Map.Entry<String, Long> p2 : photosByCamera.entrySet()) {
                if (p1.getKey() != p2.getKey()) {
                    Assert.assertTrue(p1.getValue() < p2.getValue() * 10, p2.getKey().concat(" has more than 10 times the ").concat(p1.getKey()).concat(" photos"));
                }
            }
        }


    }

    /**
     * Retrieves a List of Photos filtered by sol
     *
     * @param sol Martian rotation or day
     * @return List<Photo>
     */
    private List<Photo> getCuriosityPhotosBySol(int sol) {
        return given().
                spec(getNasaRequestSpec()).
                queryParam("sol", sol).
                when().
                get(MARS_ROVERS_CURIOSITY_PHOTOS_PATH).
                then().
                spec(getNasaResponseSpec()).
                extract().
                body().
                jsonPath().getList("photos", Photo.class);
    }

    /**
     * Retrieves a List of Photos filtered by earth date
     *
     * @param date earth date in string
     * @return List<Photo>
     */
    private List<Photo> getCuriosityPhotosByEarthDate(String date) {
        return given().
                spec(getNasaRequestSpec()).
                queryParam("earth_date", date).
                when().
                get(MARS_ROVERS_CURIOSITY_PHOTOS_PATH).
                then().
                spec(getNasaResponseSpec()).
                extract().
                body().
                jsonPath().getList("photos", Photo.class);
    }
}
