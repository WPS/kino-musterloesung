package de.wps.ddd.kino.kartenverkauf.domain;

import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzKategorie;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platzanzahl;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SaalplanTest {

    private final VorstellungId vorstellungId = new VorstellungId(UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74"));
    private Saalplan saalplan;
    private List<Platz> plaetze;

    @BeforeEach
    public void setup() {

        plaetze = new ArrayList<>();
        for (int r = 1; r <= 4; r++) {
            for (int p = 1; p <= 6; p++) {
                var reiheNr = new ReiheNummer(r);
                var platzNr = new PlatzNummer(p);
                var platzId = new PlatzId(reiheNr, platzNr);
                plaetze.add(new Platz(platzId, PlatzKategorie.Loge, true));
            }
        }

        saalplan = new Saalplan(vorstellungId, plaetze);
    }

    @Test
    public void sucheZusammenhaengendePlaetze_existsZusammenhaengendePlaetzeInSecondToLastReihe_returnsCorrectPlaetze() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var anzahlGewuenschtePlaetze = 4;
        var vorletzteReihe = 3;
        var belegtFeld = plaetze.getFirst().getClass().getDeclaredField("istVerkauft");
        belegtFeld.setAccessible(true);

        // Reihe 1 mit 4 freien Plätzen
        belegtFeld.set(plaetze.get(0), false);
        belegtFeld.set(plaetze.get(1), false);
        belegtFeld.set(plaetze.get(2), false);
        belegtFeld.set(plaetze.get(3), false);
        // Reihe 3 mit 4 freien Plätzen
        belegtFeld.set(plaetze.get(12), false);
        belegtFeld.set(plaetze.get(13), false);
        belegtFeld.set(plaetze.get(14), false);
        belegtFeld.set(plaetze.get(15), false);
        // Reihe 4 mit 4 freien, nicht zusammenhängenden Plätzen
        belegtFeld.set(plaetze.get(18), false);
        belegtFeld.set(plaetze.get(19), false);
        belegtFeld.set(plaetze.get(20), false);
        belegtFeld.set(plaetze.get(22), false);

        // act
        var zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(new Platzanzahl(anzahlGewuenschtePlaetze));

        // assert
        assertThat(zusammenhaengendePlaetze.plaetze()).hasSize(anzahlGewuenschtePlaetze);
        var platzIds = zusammenhaengendePlaetze.plaetze();
        assertThat(platzIds).hasSize(anzahlGewuenschtePlaetze);
        assertThat(zusammenhaengendePlaetze.plaetze().getLast().platz().nummer() - zusammenhaengendePlaetze.plaetze().getFirst().platz().nummer()).isEqualTo(anzahlGewuenschtePlaetze - 1);
        assertThat(zusammenhaengendePlaetze.plaetze()).allMatch(platz -> platz.reihe().nummer() == vorletzteReihe);
    }

    @Test
    public void sucheZusammenhaengendePlaetze_doesNotExistZusammenhaengendePlaetze_returnsObjectWithEmptyList() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var anzahlGewuenschtePlaetze = 2;
        var belegtFeld = plaetze.getFirst().getClass().getDeclaredField("istVerkauft");
        belegtFeld.setAccessible(true);
        belegtFeld.set(plaetze.get(0), false);
        belegtFeld.set(plaetze.get(9), false);

        // act
        var zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(new Platzanzahl(anzahlGewuenschtePlaetze));

        // assert
        assertThat(zusammenhaengendePlaetze.plaetze()).hasSize(0);
    }

    @Test
    public void markiereAlsVerkauft() {
        // arrange
        var reiheNr1 = new ReiheNummer(1);
        var platzNr1 = new PlatzNummer(1);
        var platzNr2 = new PlatzNummer(2);
        var platzNr3 = new PlatzNummer(3);
        var platzId1 = new PlatzId(reiheNr1, platzNr1);
        var platzId2 = new PlatzId(reiheNr1, platzNr2);
        var platzId3 = new PlatzId(reiheNr1, platzNr3);

        var plaetze = List.of(platzId1, platzId2, platzId3);

        var zusammenhaengendePlaetze = new ZusammenhaengendePlaetze(plaetze);

        // act
        saalplan.markiereAlsVerkauft(zusammenhaengendePlaetze);

        // assert
        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr1).isIstVerkauft()).isTrue();
        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr2).isIstVerkauft()).isTrue();
        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr3).isIstVerkauft()).isTrue();
    }

}
