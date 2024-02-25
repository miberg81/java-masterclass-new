package michael.mapLayersChallenge.impl;

import michael.mapLayersChallenge.Point;

/**
 * Parks are represented by points(lat,long) which have name and type
 */
public class Park extends Point {

    private final String name; // example "Yellowstone"

    public Park(String name, String location) {
        super(location);
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " National Park";
    }
}
