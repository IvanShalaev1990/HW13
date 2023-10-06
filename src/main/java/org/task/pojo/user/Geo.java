package org.task.pojo.user;

/**
 * Data transfer object.
 *
 * @author Shalaiev Ivan.
 * @version 1.0.0 06.10.2023
 */
public class Geo {

    private float lat;
    private float lng;

    public Geo() {
    }

    public Geo(float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
