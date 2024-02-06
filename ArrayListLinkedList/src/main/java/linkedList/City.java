package linkedList;


public class City {
    String name;
    Integer distance; // distance from Sidney;

    public City(String name, Integer distance) {
        this.name = name;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public Integer getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", distance=" + distance +
                '}'+'\n';
    }
}
