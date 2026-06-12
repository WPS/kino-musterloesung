package de.wps.ddd.kino.common.error;

public class GeschaeftsregelVerletzt extends RuntimeException {

    private GeschaeftsregelVerletzt(String message) {
        super(message);
    }

    public static void wenn(boolean condition, String message) {
        if (condition) {
            throw new GeschaeftsregelVerletzt(message);
        }
    }
}
