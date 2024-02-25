package michael.mapLayersChallenge;

public interface Mappable {

    void render();

    /**
     * Helps to constrcut lat,lon point as array
     * from the string of "lat,long" taken from Google map
     * @param location - as string in format "lat,long"
     * @return double[] - array representing lat,lon
     */
    static double[] stringToLatLon(String location) {
        var splits = location.split(",");
        double lat = Double.valueOf(splits[0]);
        double lon = Double.valueOf(splits[1]);
        return new double[]{lat, lon};
    }

}
