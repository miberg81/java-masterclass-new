package michael.advanced.wildcardsAndGenericMethods.query;

public interface QueryItem {
    public boolean matchFieldValue(String fieldName, String value);
}
