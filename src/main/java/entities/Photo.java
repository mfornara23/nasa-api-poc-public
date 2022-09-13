package entities;

import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({"id","sol","camera","imgSrc","earthDate","rover"})
public class Photo {

    private String id;
    private Integer sol;
    private String imgSrc;
    private String earthDate;
    private Rover rover;
    private Camera camera;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSol() {
        return sol;
    }

    public void setSol(Integer sol) {
        this.sol = sol;
    }

    @JsonProperty("img_src")
    public String getImgSrc() {
        return imgSrc;
    }

    @JsonProperty("img_src")
    public void setImg_src(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    @JsonProperty("earth_date")
    public String getEarthDate() {
        return earthDate;
    }

    @JsonProperty("earth_date")
    public void setEarth_date(String earthDate) {
        this.earthDate = earthDate;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
