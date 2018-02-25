package entities;

/**
 * Created by George-Lenovo on 02/25/2018.
 */
public enum Gender {
    FEMALE,
    MALE;

    public static String getSimpleValue(Gender gender) {
        return gender.toString().toLowerCase();
    }

    public static String parseValue(Gender gender) {
        String firstLetter = String.valueOf(gender.toString().charAt(0));
        String rest = gender.toString().substring(1).toLowerCase();
        return firstLetter.concat(rest);
    }
}
