package de.wps.ddd.kino.kartenverkauf.application.services;

import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platzanzahl;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.SucheZusammenhaengendePlaetze;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SucheZusammenhaengendePlaetzeIntegrationTest {

    @Autowired
    private SucheZusammenhaengendePlaetze sucheZusammenhaengendePlaetze;

    private final VorstellungId vorstellungId =
            new VorstellungId(UUID.fromString("090c173a-3636-4980-865a-1ec859eb4f90"));

    @Test
    void sucheZusammenhaengendePlaetze_vierPlaetze_liefertViererBlockInReihe4() {
        // act
        var plaetze = sucheZusammenhaengendePlaetze.fuer(vorstellungId, new Platzanzahl(4));

        // assert
        assertThat(plaetze.plaetze()).hasSize(4);
        assertThat(plaetze.plaetze()).allMatch(p -> p.reihe().nummer() == 4);
        assertThat(plaetze.plaetze()).extracting(p -> p.platz().nummer()).containsExactly(1, 2, 3, 4);
    }

    @Test
    void sucheZusammenhaengendePlaetze_mehrAlsEineReihe_liefertLeereListe() {
        // arrange — eine Reihe fasst 8 Plätze, 9 zusammenhängende gibt es nie
        // act
        var plaetze = sucheZusammenhaengendePlaetze.fuer(vorstellungId, new Platzanzahl(9));

        // assert
        assertThat(plaetze.plaetze()).isEmpty();
    }
}
