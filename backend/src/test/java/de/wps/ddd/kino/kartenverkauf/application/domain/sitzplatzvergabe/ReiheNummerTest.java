package de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReiheNummerTest {

    @Test
    void compareTo_vergleichtNumerisch() {
        // act / assert
        assertThat(new ReiheNummer(1)).isLessThan(new ReiheNummer(2));
        assertThat(new ReiheNummer(5)).isGreaterThan(new ReiheNummer(2));
        assertThat(new ReiheNummer(3)).isEqualByComparingTo(new ReiheNummer(3));
    }
}
