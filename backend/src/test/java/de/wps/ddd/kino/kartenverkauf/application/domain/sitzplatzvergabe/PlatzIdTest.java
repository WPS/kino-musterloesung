package de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlatzIdTest {

    @Test
    void gleichheit_basiertAufReiheUndPlatz() {
        // arrange
        var platzId = new PlatzId(new ReiheNummer(4), new PlatzNummer(2));

        // act / assert
        assertThat(platzId).isEqualTo(new PlatzId(new ReiheNummer(4), new PlatzNummer(2)));
        assertThat(platzId).isNotEqualTo(new PlatzId(new ReiheNummer(4), new PlatzNummer(3)));
        assertThat(platzId).isNotEqualTo(new PlatzId(new ReiheNummer(3), new PlatzNummer(2)));
    }
}
