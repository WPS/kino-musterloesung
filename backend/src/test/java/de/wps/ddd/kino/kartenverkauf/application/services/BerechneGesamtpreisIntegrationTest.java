package de.wps.ddd.kino.kartenverkauf.application.services;

import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.BerechneGesamtpreis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BerechneGesamtpreisIntegrationTest {

    @Autowired
    private BerechneGesamtpreis berechneGesamtpreis;

    private final VorstellungId vorstellungId =
            new VorstellungId(UUID.fromString("090c173a-3636-4980-865a-1ec859eb4f90"));

    @Test
    void berechneGesamtpreis_vierPlaetzeInReihe4_liefertGesamtpreis() {
        // arrange — Reihe 4, Plätze 1-4 (Standard 10,50 + Loge 2,00 je Platz)
        var gewaehltePlaetze = new ZusammenhaengendePlaetze(List.of(
                platzId(4, 1), platzId(4, 2), platzId(4, 3), platzId(4, 4)));

        // act
        var preis = berechneGesamtpreis.fuer(vorstellungId, gewaehltePlaetze);

        // assert
        assertThat(preis).isEqualTo(Geldbetrag.euro(50, 0));
    }

    private static PlatzId platzId(int reihe, int platz) {
        return new PlatzId(new ReiheNummer(reihe), new PlatzNummer(platz));
    }
}
