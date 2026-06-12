package de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlatzNummerTest {

    @Test
    void compareTo_vergleichtNumerisch() {
        // act / assert
        assertThat(new PlatzNummer(1)).isLessThan(new PlatzNummer(2));
        assertThat(new PlatzNummer(5)).isGreaterThan(new PlatzNummer(2));
        assertThat(new PlatzNummer(3)).isEqualByComparingTo(new PlatzNummer(3));
    }
}
