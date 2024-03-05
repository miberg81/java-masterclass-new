package michael.advanced.wildcardsAndGenericMethods;

public class LPAStudent extends Student {

    private double percentComplete;

    public LPAStudent() {
        // super constructor will be called implicitly!
        percentComplete = random.nextDouble(0, 100.001);
    }

    @Override
    public String toString() {
        // second % sign is to print a percent sign literal on the screen!
        return "%s %8.1f%%".formatted(super.toString(), percentComplete);
    }

    public double getPercentComplete() {
        return percentComplete;
    }
}
