package de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ZusammenhaengendePlaetzeTest {

    @Test
    void anzahl_entsprichtAnzahlDerPlaetze() {
        // arrange
        var plaetze = new ZusammenhaengendePlaetze(List.of(platzId(1, 1), platzId(1, 2), platzId(1, 3)));

        // act / assert
        assertThat(plaetze.anzahl()).isEqualTo(new Platzanzahl(3));
    }

    @Test
    void anzahl_keinePlaetze_istNull() {
        // arrange
        var plaetze = new ZusammenhaengendePlaetze(List.of());

        // act / assert
        assertThat(plaetze.anzahl()).isEqualTo(new Platzanzahl(0));
    }

    private static PlatzId platzId(int reihe, int platz) {
        return new PlatzId(new ReiheNummer(reihe), new PlatzNummer(platz));
    }
}
