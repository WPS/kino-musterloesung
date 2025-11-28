package de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record PlatzNummer(int nummer) implements Comparable<PlatzNummer> {
    @Override
    public int compareTo(PlatzNummer o) {
        return Integer.compare(this.nummer, o.nummer);
    }
}
