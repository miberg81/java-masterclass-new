package filesAndPaths.lpa.dataStreams;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

class Player implements Serializable {

    private final static long serialVersionUID = 1L;
    private final static int version = 2;
    private String name;
    private long topScore;
    private long bigScore;
    private final transient long accountId;
    private List<String> collectedWeapons = new LinkedList<>();

    public Player(long accountId, String name, int topScore, List<String> collectedWeapons) {
        this.accountId = accountId;
        this.name = name;
        this.topScore = topScore;
        this.collectedWeapons = collectedWeapons;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + accountId + ", " +
                "name='" + name + '\'' +
                ", topScore=" + topScore +
                ", collectedWeapons=" + collectedWeapons +
                '}';
    }

    @Serial
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
//        stream.defaultReadObject();
//        bigScore = (bigScore == 0) ? 1_000_000_000L : bigScore;

        var serializedVer = stream.readInt();
        collectedWeapons = (List<String>) stream.readObject();
        name = stream.readUTF();
        topScore = (serializedVer == 1) ? stream.readInt() : stream.readLong();
    }

    @Serial
    private void writeObject(ObjectOutputStream stream)
            throws IOException {

        System.out.println("--> Customized Writing");
        stream.writeInt(version);
        stream.writeObject(collectedWeapons);
        stream.writeUTF(name);
        stream.writeLong(topScore);
    }
}
