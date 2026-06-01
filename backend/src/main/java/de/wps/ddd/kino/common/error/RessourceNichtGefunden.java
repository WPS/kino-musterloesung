package de.wps.ddd.kino.common.error;

public class RessourceNichtGefunden extends RuntimeException {

    public RessourceNichtGefunden(String message) {
        super(message);
    }

    public static void throwIf(boolean condition, String message) {
        if (condition) {
            throw new RessourceNichtGefunden(message);
        }
    }
}
