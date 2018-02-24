package entities;

/**
 * Created by George-Lenovo on 02/24/2018.
 */
public enum SectorType {
    MEDICINE, CAR, FOOD, DOMESTIC, SECURITY;

    public static String getComplexValue(SectorType breed) {
        if (breed.equals(MEDICINE)) return "Medicine";
        else if (breed.equals(CAR)) return "Car";
        else if (breed.equals(FOOD)) return "Food";
        else if (breed.equals(DOMESTIC)) return "Domestic";
        else if (breed.equals(SECURITY)) return "Security";
        return null;
    }

    public static String getSimpleValue(SectorType sector) {
        return sector.toString().toLowerCase();
    }
}
