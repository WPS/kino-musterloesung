package de.wps.ddd.kino.kartenverkauf.adapters.persistence.repositories;

import de.wps.ddd.kino.common.error.RessourceNichtGefunden;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsstatus;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsvorgang;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.Zahlungsvorgaenge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ZahlungsvorgaengeIntegrationTest {

    @Autowired
    private Zahlungsvorgaenge zahlungsvorgaenge;

    @Test
    void speichereUndHole_liestGespeichertenZahlungsvorgang() {
        // arrange
        var zahlungsvorgang = Zahlungsvorgang.starteZahlung();

        // act
        zahlungsvorgaenge.speichere(zahlungsvorgang);
        var geladen = zahlungsvorgaenge.hole(zahlungsvorgang.getAuftragsnummer());

        // assert
        assertThat(geladen.getAuftragsnummer()).isEqualTo(zahlungsvorgang.getAuftragsnummer());
        assertThat(geladen.getStatus()).isEqualTo(Zahlungsstatus.Ausstehend);
    }

    @Test
    void hole_unbekannteAuftragsnummer_wirftRessourceNichtGefunden() {
        // arrange
        var unbekannteAuftragsnummer = new Auftragsnummer(UUID.randomUUID());

        // act / assert
        assertThatThrownBy(() -> zahlungsvorgaenge.hole(unbekannteAuftragsnummer))
                .isInstanceOf(RessourceNichtGefunden.class)
                .hasMessageContaining("existiert nicht");
    }
}
