package de.wps.ddd.kino.kartenverkauf.application.services;

import de.wps.ddd.kino.common.error.RessourceNichtGefunden;
import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.ZahlungAbgebrochen;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.ZahlungEingegangen;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsstatus;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.Zahlung;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ZahlungIntegrationTest {

    @Autowired
    private Zahlung zahlung;

    private final VorstellungId vorstellungId =
            new VorstellungId(UUID.fromString("090c173a-3636-4980-865a-1ec859eb4f90"));
    private final ZusammenhaengendePlaetze plaetze =
            new ZusammenhaengendePlaetze(List.of(new PlatzId(new ReiheNummer(4), new PlatzNummer(1))));

    @Test
    void starteZahlungsvorgang_liefertAusstehendenVorgang() {
        // act
        var auftragsnummer = zahlung.starteZahlungsvorgang(Geldbetrag.euro(50, 0), vorstellungId, plaetze);

        // assert
        assertThat(zahlung.status(auftragsnummer)).isEqualTo(Zahlungsstatus.Ausstehend);
    }

    @Test
    void verarbeite_zahlungEingegangen_setztStatusEingegangen() {
        // arrange
        var auftragsnummer = zahlung.starteZahlungsvorgang(Geldbetrag.euro(50, 0), vorstellungId, plaetze);

        // act
        zahlung.verarbeite(new ZahlungEingegangen(auftragsnummer));

        // assert
        assertThat(zahlung.status(auftragsnummer)).isEqualTo(Zahlungsstatus.Eingegangen);
    }

    @Test
    void verarbeite_zahlungAbgebrochen_setztStatusAbgebrochen() {
        // arrange
        var auftragsnummer = zahlung.starteZahlungsvorgang(Geldbetrag.euro(50, 0), vorstellungId, plaetze);

        // act
        zahlung.verarbeite(new ZahlungAbgebrochen(auftragsnummer));

        // assert
        assertThat(zahlung.status(auftragsnummer)).isEqualTo(Zahlungsstatus.Abgebrochen);
    }

    @Test
    void status_unbekannteAuftragsnummer_wirftRessourceNichtGefunden() {
        // arrange
        var unbekannteAuftragsnummer = new Auftragsnummer(UUID.randomUUID());

        // act / assert
        assertThatThrownBy(() -> zahlung.status(unbekannteAuftragsnummer))
                .isInstanceOf(RessourceNichtGefunden.class)
                .hasMessageContaining("existiert nicht");
    }
}
