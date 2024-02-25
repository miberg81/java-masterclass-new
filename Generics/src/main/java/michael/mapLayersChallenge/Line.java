package michael.mapLayersChallenge;

import java.util.Arrays;

/**
 * This class is a general class for rivers, roads and other mappable entities,
 *  which represent a line. It is abstract because it should NOT be instantiated.
 *  Instead, concrete entities like rivers, roads should be.
 */
public abstract class Line implements Mappable {

    private final double[][] locations;

    public Line(String... locations) {
        this.locations = new double[locations.length][];
        int index = 0;
        for (var l : locations) {
            this.locations[index++] = Mappable.stringToLatLon(l);
        }
    }

    @Override
    public void render() {
        System.out.println("Render " + this + " as LINE (" + locations() + ")");
    }

    private String locations() {
        return Arrays.deepToString(locations);
    }
}
