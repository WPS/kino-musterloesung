package de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe;

import de.wps.ddd.kino.common.error.GeschaeftsregelVerletzt;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SaalplanTest {

    private final VorstellungId vorstellungId = new VorstellungId(UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74"));

    @Test
    void sucheZusammenhaengendePlaetze_zusammenhaengendePlaetzeInVorletzterReihe_liefertPlaetze() {
        // arrange
        var anzahlGewuenschtePlaetze = 4;
        var vorletzteReihe = 3;
        var saalplan = new Saalplan(vorstellungId, List.of(
                // Reihe 1 mit 4 freien Plätzen
                frei(1, 1), frei(1, 2), frei(1, 3), frei(1, 4), verkauft(1, 5), verkauft(1, 6),
                // Reihe 2 ohne freie Plätze
                verkauft(2, 1), verkauft(2, 2), verkauft(2, 3), verkauft(2, 4), verkauft(2, 5), verkauft(2, 6),
                // Reihe 3 mit 4 freien Plätzen -> Treffer
                frei(3, 1), frei(3, 2), frei(3, 3), frei(3, 4), verkauft(3, 5), verkauft(3, 6),
                // Reihe 4 mit 4 freien, nicht zusammenhängenden Plätzen
                frei(4, 1), frei(4, 2), frei(4, 3), verkauft(4, 4), frei(4, 5), verkauft(4, 6)));

        // act
        var zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(new Platzanzahl(anzahlGewuenschtePlaetze));

        // assert
        assertThat(zusammenhaengendePlaetze.plaetze()).hasSize(anzahlGewuenschtePlaetze);
        assertThat(zusammenhaengendePlaetze.plaetze()).allMatch(p -> p.reihe().nummer() == vorletzteReihe);
        var platzNummern = zusammenhaengendePlaetze.plaetze().stream().map(p -> p.platz().nummer()).toList();
        assertThat(platzNummern.getLast() - platzNummern.getFirst()).isEqualTo(anzahlGewuenschtePlaetze - 1);
    }

    @Test
    void sucheZusammenhaengendePlaetze_keineZusammenhaengendenPlaetze_liefertLeereListe() {
        // arrange — nur isolierte freie Plaetze, keine zwei zusammenhaengend
        var saalplan = new Saalplan(vorstellungId, List.of(
                frei(1, 1), verkauft(1, 2), verkauft(1, 3), verkauft(1, 4), verkauft(1, 5), verkauft(1, 6),
                verkauft(2, 1), verkauft(2, 2), verkauft(2, 3), frei(2, 4), verkauft(2, 5), verkauft(2, 6)));

        // act
        var zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(new Platzanzahl(2));

        // assert
        assertThat(zusammenhaengendePlaetze.plaetze()).isEmpty();
    }

    @Test
    void sucheZusammenhaengendePlaetze_keinePlaetzeAngefragt_wirftException() {
        // arrange
        var saalplan = new Saalplan(vorstellungId, List.of(frei(1, 1), frei(1, 2), frei(1, 3)));

        // act / assert
        assertThatThrownBy(() -> saalplan.sucheZusammenhaengendePlaetze(new Platzanzahl(0)))
                .isInstanceOf(GeschaeftsregelVerletzt.class)
                .hasMessageContaining("Mindestens ein Platz muss angefragt werden");
    }

    @Test
    void markiereAlsVerkauft_markiertZusammenhaengendePlaetzeAlsVerkauft() {
        // arrange
        var saalplan = new Saalplan(vorstellungId, List.of(frei(1, 1), frei(1, 2), frei(1, 3)));
        var zuVerkaufen = new ZusammenhaengendePlaetze(List.of(platzId(1, 1), platzId(1, 2), platzId(1, 3)));

        // act
        saalplan.markiereAlsVerkauft(zuVerkaufen);

        // assert
        assertThat(saalplan.platz(platzId(1, 1)).istVerkauft()).isTrue();
        assertThat(saalplan.platz(platzId(1, 2)).istVerkauft()).isTrue();
        assertThat(saalplan.platz(platzId(1, 3)).istVerkauft()).isTrue();
    }

    private static Platz frei(int reihe, int platz) {
        return new Platz(platzId(reihe, platz), PlatzKategorie.Loge, false);
    }

    private static Platz verkauft(int reihe, int platz) {
        return new Platz(platzId(reihe, platz), PlatzKategorie.Loge, true);
    }

    private static PlatzId platzId(int reihe, int platz) {
        return new PlatzId(new ReiheNummer(reihe), new PlatzNummer(platz));
    }
}
