package iftm.pedro.aproject.utils;

import org.springframework.data.geo.Point;
import static java.lang.Math.*;

public class Utils {
    static final Long EARTH_RADIUS = 6371L;

    private static double toRadians(double value){
        return value * (PI / 180);
    }

    private static double getHaversine(double value){
        return abs(pow(sin(value / 2),2));
    }

    public static Double calculateDistance(Point origin, Point destiny){
        double originRadY = toRadians(origin.getY());
        double destinyRadY = toRadians(destiny.getY());
        double deltaX = toRadians(destiny.getX()) - toRadians(origin.getX());
        double harvSqrt = sqrt(getHaversine(destinyRadY - originRadY) + (cos(originRadY) * cos(destinyRadY) * getHaversine(deltaX)));
        return 2 * EARTH_RADIUS * asin(harvSqrt);
    }
}
