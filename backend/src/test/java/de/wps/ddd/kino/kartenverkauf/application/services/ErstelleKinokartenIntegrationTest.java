package de.wps.ddd.kino.kartenverkauf.application.services;

import de.wps.ddd.kino.common.error.GeschaeftsregelVerletzt;
import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.ZahlungEingegangen;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.ErstelleKinokarten;
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
class ErstelleKinokartenIntegrationTest {

    @Autowired
    private ErstelleKinokarten erstelleKinokarten;

    @Autowired
    private Zahlung zahlung;

    private final VorstellungId vorstellungId =
            new VorstellungId(UUID.fromString("090c173a-3636-4980-865a-1ec859eb4f90"));
    private final ZusammenhaengendePlaetze gewaehltePlaetze = new ZusammenhaengendePlaetze(List.of(
            platzId(4, 1), platzId(4, 2), platzId(4, 3), platzId(4, 4)));

    @Test
    void erstelleKinokarten_zahlungEingegangen_liefertKinokartenFuerGewaehltePlaetze() {
        // arrange
        var auftragsnummer = zahlung.starteZahlungsvorgang(Geldbetrag.euro(50, 0), vorstellungId, gewaehltePlaetze);
        zahlung.verarbeite(new ZahlungEingegangen(auftragsnummer));

        // act
        var kinokarten = erstelleKinokarten.fuer(auftragsnummer, vorstellungId, gewaehltePlaetze);

        // assert
        assertThat(kinokarten).hasSize(4);
        assertThat(kinokarten).allSatisfy(karte -> {
            assertThat(karte.getFilm().name()).isEqualTo("The Fast and the Curious");
            assertThat(karte.getSaal().name()).isEqualTo("kleiner Saal");
            assertThat(karte.getReihe().nummer()).isEqualTo(4);
        });
        assertThat(kinokarten).extracting(karte -> karte.getPlatz().nummer()).containsExactly(1, 2, 3, 4);
    }

    @Test
    void erstelleKinokarten_zahlungNichtEingegangen_wirftGeschaeftsregelVerletzt() {
        // arrange
        var auftragsnummer = zahlung.starteZahlungsvorgang(Geldbetrag.euro(50, 0), vorstellungId, gewaehltePlaetze);

        // act / assert
        assertThatThrownBy(() -> erstelleKinokarten.fuer(auftragsnummer, vorstellungId, gewaehltePlaetze))
                .isInstanceOf(GeschaeftsregelVerletzt.class);
    }

    private static PlatzId platzId(int reihe, int platz) {
        return new PlatzId(new ReiheNummer(reihe), new PlatzNummer(platz));
    }
}
