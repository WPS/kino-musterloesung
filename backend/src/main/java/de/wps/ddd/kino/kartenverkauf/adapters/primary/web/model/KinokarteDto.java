package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model;

public record KinokarteDto(
        String film,
        String beginn,
        String saal,
        int reihe,
        int platz
) {
}
