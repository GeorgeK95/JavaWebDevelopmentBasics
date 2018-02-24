package entities;

/**
 * Created by George-Lenovo on 02/23/2018.
 */
public enum BreedType {
    STREET_TRANSCENDED,
    AMERICAN_SHORTHAIR,
    MUNCHKIN,
    SIAMESE;

    public static BreedType parseValue(String str) {
        if (str.equals("Street Transcended")) return STREET_TRANSCENDED;
        if (str.equals("American Shorthair")) return AMERICAN_SHORTHAIR;
        if (str.equals("Munchkin")) return MUNCHKIN;
        if (str.equals("Siamese")) return SIAMESE;
        return null;
    }

    public static String getSimpleValue(BreedType breed) {
        return breed.toString().toLowerCase().replace("_", "-");
    }

    /*public static String getSimpleValue(BreedType type) {
        String firstChar = String.valueOf(Character.toUpperCase(type.toString().charAt(0)));
        String other = type.toString().substring(1);
        return firstChar.concat(other);
    }*/

    public static String getComplexValue(BreedType breed) {
        if (breed.equals(MUNCHKIN)) return "Munchkin";
        else if (breed.equals(STREET_TRANSCENDED)) return "Street Transcended";
        else if (breed.equals(SIAMESE)) return "Siamese";
        else if (breed.equals(AMERICAN_SHORTHAIR)) return "American Shorthair";
        return null;
    }
}
