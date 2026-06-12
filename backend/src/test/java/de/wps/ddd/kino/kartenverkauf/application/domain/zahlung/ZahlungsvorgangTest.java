package de.wps.ddd.kino.kartenverkauf.application.domain.zahlung;

import de.wps.ddd.kino.common.error.GeschaeftsregelVerletzt;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ZahlungsvorgangTest {

    @Test
    void starteZahlung_istAusstehendMitAuftragsnummer() {
        // act
        var zahlungsvorgang = Zahlungsvorgang.starteZahlung();

        // assert
        assertThat(zahlungsvorgang.getStatus()).isEqualTo(Zahlungsstatus.Ausstehend);
        assertThat(zahlungsvorgang.getAuftragsnummer()).isNotNull();
        assertThat(zahlungsvorgang.getAuftragsnummer().nummer()).isNotNull();
    }

    @Test
    void zahlungEingegangen_ausAusstehend_wirdEingegangen() {
        // arrange
        var zahlungsvorgang = Zahlungsvorgang.starteZahlung();

        // act
        zahlungsvorgang.zahlungEingegangen();

        // assert
        assertThat(zahlungsvorgang.getStatus()).isEqualTo(Zahlungsstatus.Eingegangen);
    }

    @Test
    void zahlungAbgebrochen_ausAusstehend_wirdAbgebrochen() {
        // arrange
        var zahlungsvorgang = Zahlungsvorgang.starteZahlung();

        // act
        zahlungsvorgang.zahlungAbgebrochen();

        // assert
        assertThat(zahlungsvorgang.getStatus()).isEqualTo(Zahlungsstatus.Abgebrochen);
    }

    @Test
    void zahlungEingegangen_bereitsEingegangen_wirftGeschaeftsregelVerletzt() {
        // arrange
        var zahlungsvorgang = Zahlungsvorgang.starteZahlung();
        zahlungsvorgang.zahlungEingegangen();

        // act / assert
        assertThatThrownBy(zahlungsvorgang::zahlungEingegangen)
                .isInstanceOf(GeschaeftsregelVerletzt.class);
    }

    @Test
    void zahlungAbgebrochen_bereitsEingegangen_wirftGeschaeftsregelVerletzt() {
        // arrange
        var zahlungsvorgang = Zahlungsvorgang.starteZahlung();
        zahlungsvorgang.zahlungEingegangen();

        // act / assert
        assertThatThrownBy(zahlungsvorgang::zahlungAbgebrochen)
                .isInstanceOf(GeschaeftsregelVerletzt.class);
    }

    @Test
    void zahlungEingegangen_bereitsAbgebrochen_wirftGeschaeftsregelVerletzt() {
        // arrange
        var zahlungsvorgang = Zahlungsvorgang.starteZahlung();
        zahlungsvorgang.zahlungAbgebrochen();

        // act / assert
        assertThatThrownBy(zahlungsvorgang::zahlungEingegangen)
                .isInstanceOf(GeschaeftsregelVerletzt.class);
    }
}
