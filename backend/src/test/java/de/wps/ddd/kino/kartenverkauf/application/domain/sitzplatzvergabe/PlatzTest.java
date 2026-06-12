package de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlatzTest {

    private final PlatzId platzId = new PlatzId(new ReiheNummer(1), new PlatzNummer(1));

    @Test
    void neuerPlatz_nichtVerkauft_istFrei() {
        // act
        var platz = new Platz(platzId, PlatzKategorie.Parkett, false);

        // assert
        assertThat(platz.getId()).isEqualTo(platzId);
        assertThat(platz.getKategorie()).isEqualTo(PlatzKategorie.Parkett);
        assertThat(platz.istVerkauft()).isFalse();
        assertThat(platz.istFrei()).isTrue();
    }

    @Test
    void neuerPlatz_bereitsVerkauft_istNichtFrei() {
        // act
        var platz = new Platz(platzId, PlatzKategorie.Loge, true);

        // assert
        assertThat(platz.getId()).isEqualTo(platzId);
        assertThat(platz.getKategorie()).isEqualTo(PlatzKategorie.Loge);
        assertThat(platz.istVerkauft()).isTrue();
        assertThat(platz.istFrei()).isFalse();
    }

    @Test
    void markiereAlsVerkauft_machtPlatzVerkauft() {
        // arrange
        var platz = new Platz(platzId, PlatzKategorie.Loge, false);

        // act
        platz.markiereAlsVerkauft();

        // assert
        assertThat(platz.istVerkauft()).isTrue();
        assertThat(platz.istFrei()).isFalse();
    }
}
