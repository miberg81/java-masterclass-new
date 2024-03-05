package michael.advanced.wildcardsAndGenericMethodsChallenge;

public class LPAStudent1 extends Student1 {

    private double percentComplete;

    public LPAStudent1() {
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

    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        if (fieldName.equalsIgnoreCase("percentComplete")) {
            return percentComplete <= Integer.parseInt(value);
        }
        return super.matchFieldValue(fieldName, value);
    }
}

