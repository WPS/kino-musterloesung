package de.wps.ddd.kino.common.error;

public class RessourceNichtGefunden extends RuntimeException {

    private RessourceNichtGefunden(String message) {
        super(message);
    }

    public static void wenn(boolean condition, String message) {
        if (condition) {
            throw new RessourceNichtGefunden(message);
        }
    }
}
