package de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record PlatzId(ReiheNummer reihe, PlatzNummer platz) {
}
