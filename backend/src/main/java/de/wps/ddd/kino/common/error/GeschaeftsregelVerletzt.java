package de.wps.ddd.kino.common.error;

public class GeschaeftsregelVerletzt extends RuntimeException {

    public GeschaeftsregelVerletzt(String message) {
        super(message);
    }

    public static void throwIf(boolean condition, String message) {
        if (condition) {
            throw new GeschaeftsregelVerletzt(message);
        }
    }
}
