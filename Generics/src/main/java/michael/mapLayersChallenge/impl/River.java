package michael.mapLayersChallenge.impl;

import michael.mapLayersChallenge.Line;

/**
 * Parks are represented by lines with multiple points on them with(lat,long)
 * which have name and type
 */
public class River extends Line {

    private final String name; // example "Mississippi"

    public River(String name, String... locations) {
        super(locations);
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " River";
    }
}
