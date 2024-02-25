package michael.mapLayersChallenge;

import java.util.Arrays;

/*
This class is a general class for parks, parking lots, stores and other mappable entities,
 which represent a point on  a map
 */
public abstract class Point implements Mappable {

    private double[] location = new double[2];

    public Point(String location) {
        this.location = Mappable.stringToLatLon(location);
    }

    @Override
    public void render() {
        System.out.println("Render " + this + " as POINT (" + location() + ")");
    }

    private String location() {
        return Arrays.toString(location);
    }

}
