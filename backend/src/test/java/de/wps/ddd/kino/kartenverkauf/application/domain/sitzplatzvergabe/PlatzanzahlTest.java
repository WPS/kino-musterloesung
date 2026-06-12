package de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlatzanzahlTest {

    @Test
    void konstruktor_positiverWert_wirdAkzeptiert() {
        // act
        var platzanzahl = new Platzanzahl(4);

        // assert
        assertThat(platzanzahl.value()).isEqualTo(4);
    }

    @Test
    void konstruktor_null_wirdAkzeptiert() {
        // act
        var platzanzahl = new Platzanzahl(0);

        // assert
        assertThat(platzanzahl.value()).isEqualTo(0);
    }

    @Test
    void konstruktor_negativerWert_wirftException() {
        // act / assert
        assertThatThrownBy(() -> new Platzanzahl(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Platzanzahl darf nicht negativ sein");
    }
}
