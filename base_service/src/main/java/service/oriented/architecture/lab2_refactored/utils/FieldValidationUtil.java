package service.oriented.architecture.lab2_refactored.utils;

import service.oriented.architecture.lab2_refactored.enums.Color;
import service.oriented.architecture.lab2_refactored.enums.MovieGenre;
import service.oriented.architecture.lab2_refactored.enums.MpaaRating;
import service.oriented.architecture.lab2_refactored.exceptions.EntityIsNotValidException;

public class FieldValidationUtil {

    public static Integer getIntegerFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse integer value from " + value);
        }
    }

    public static Double getDoubleFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse double value from " + value);
        }
    }

    public static Float getFloatFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse double value from " + value);
        }
    }

    public static Long getLongFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse double value from " + value);
        }
    }

    public static String getStringValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        return value;
    }

    public static Color getColorValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Color.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new EntityIsNotValidException("Color does not exist " + value);
        }
    }

    public static MovieGenre getMovieGenreValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return MovieGenre.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new EntityIsNotValidException("Movie genre does not exist " + value);
        }
    }

    public static MpaaRating getMpaaRatingValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return MpaaRating.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new EntityIsNotValidException("Mpaa rating does not exist " + value);
        }
    }

    private static boolean isEmptyOrNull(String value) {
        if (value == null || value.equals("null"))
            return true;
        value = value.trim();
        return value.isEmpty();
    }

}
