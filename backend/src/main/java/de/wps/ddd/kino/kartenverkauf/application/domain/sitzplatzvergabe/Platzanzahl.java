package de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

@ValueObject
public record Platzanzahl(int value) {
    public Platzanzahl {
        Assert.isTrue(value >= 0, "Platzanzahl darf nicht negativ sein.");
    }
}
