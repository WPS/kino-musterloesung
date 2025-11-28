package de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.List;

@ValueObject
public record ZusammenhaengendePlaetze(List<PlatzId> plaetze) {
    public Platzanzahl anzahl() {
        return new Platzanzahl(plaetze.size());
    }
}
