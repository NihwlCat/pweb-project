package iftm.pedro.aproject.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.geo.Point;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    private String locale;
    private int number;
    private Point coordinates;

    public Address() {
    }

    public Address(String locale, int number, Point coordinates) {
        this.locale = locale;
        this.number = number;
        this.coordinates = coordinates;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }
}
