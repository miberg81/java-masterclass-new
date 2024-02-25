package michael.mapLayersChallenge;

import michael.mapLayersChallenge.impl.Layer;
import michael.mapLayersChallenge.impl.Park;
import michael.mapLayersChallenge.impl.River;

public class MainLayeredMapsChallenge {


    public static void main(String[] args) {

        // PREPARE PARKS
        var parks = new Park[] {
                new Park("Grand Canyon", "40.1021,-75.4231"),
                new Park("Echt","35.1021,-27.4231"),
                new Park("Stam park","39.1021,27.424")
        };
        Layer<Park> parksLayer = new Layer<>(parks);
        parksLayer.renderLayer();

        // PREPARE REVERS
        var rivers = new River[] {
                new River("Missisipi", "47.216,-95.2348", "29.1566,-89.2495", "27.1566,-89.2495"),
                new River("Stam river", "44.216,-95.2348", "22.166,-89.2495", "29.1566,-89.2495"),
        };
        Layer<River> riversLayer = new Layer<>(rivers);
        riversLayer.addElements(
                new River("Stam2 river", "34.216,-95.2348", "22.166,-89.2495", "29.1566,-89.2495"),
                new River("Stam3 river", "14.216,-95.2348", "22.166,-89.2495", "29.1566,-89.2495")
        );
        riversLayer.renderLayer();

    }
}
