package de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeldbetragTest {

    @Test
    void euroInCent_setztBetragInCent() {
        // act
        var betrag = Geldbetrag.euroInCent(150);

        // assert
        assertThat(betrag.getBetrag()).isEqualTo(150);
        assertThat(betrag.getWaehrung()).isEqualTo(Geldbetrag.Waehrung.EUR);
    }

    @Test
    void euro_rechnetEuroUndCentInCentUm() {
        // act
        var betrag = Geldbetrag.euro(10, 50);

        // assert
        assertThat(betrag.getBetrag()).isEqualTo(1050);
    }

    @Test
    void plus_addiertBetraege() {
        // act
        var summe = Geldbetrag.euro(10, 50).plus(Geldbetrag.euro(2, 0));

        // assert
        assertThat(summe).isEqualTo(Geldbetrag.euro(12, 50));
    }

    @Test
    void mal_vervielfachtBetrag() {
        // act
        var betrag = Geldbetrag.euro(10, 50).mal(4);

        // assert
        assertThat(betrag).isEqualTo(Geldbetrag.euro(42, 0));
    }

    @Test
    void mal_null_ergibtNull() {
        // act
        var betrag = Geldbetrag.euro(10, 50).mal(0);

        // assert
        assertThat(betrag).isEqualTo(Geldbetrag.euroInCent(0));
    }

    @Test
    void euroInCent_negativerBetrag_wirftException() {
        // act / assert
        assertThatThrownBy(() -> Geldbetrag.euroInCent(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Betrag muss größer gleich 0 sein");
    }

    @Test
    void euro_negativerBetrag_wirftException() {
        // act / assert
        assertThatThrownBy(() -> Geldbetrag.euro(-1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Betrag muss größer gleich 0 sein");
    }

    @Test
    void mal_negativeAnzahl_wirftException() {
        // act / assert
        assertThatThrownBy(() -> Geldbetrag.euro(10, 50).mal(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Anzahl muss größer gleich 0 sein");
    }

    @Test
    void gleichheit_basiertAufBetragUndWaehrung() {
        // act / assert
        assertThat(Geldbetrag.euro(10, 50)).isEqualTo(Geldbetrag.euroInCent(1050));
        assertThat(Geldbetrag.euro(10, 50)).isNotEqualTo(Geldbetrag.euro(10, 51));
    }
}
