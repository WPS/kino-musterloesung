package de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf;

import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzKategorie;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Beginn;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Film;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Saal;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungKategorie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PreisberechnungTest {

    private final Preisberechnung preisberechnung = new Preisberechnung();
    private final VorstellungId vorstellungId = new VorstellungId(UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74"));

    @ParameterizedTest
    @CsvSource({
            "Standard,   Parkett, 10, 50",
            "Standard,   Loge,    12, 50",
            "PrimeTime,  Parkett, 12,  0",
            "PrimeTime,  Loge,    14,  0",
            "Kinderfilm, Parkett,  8, 50",
            "Kinderfilm, Loge,    10, 50",
    })
    void ermittlePreis_einPlatz_kombiniertVorstellungsUndPlatzpreis(
            VorstellungKategorie vorstellungKategorie, PlatzKategorie platzKategorie, int euro, int cent) {
        // arrange
        var vorstellung = vorstellung(vorstellungKategorie);
        var platzId = new PlatzId(new ReiheNummer(1), new PlatzNummer(1));
        var saalplan = new Saalplan(vorstellungId, List.of(new Platz(platzId, platzKategorie, false)));

        // act
        var preis = preisberechnung.ermittlePreis(vorstellung, saalplan, new ZusammenhaengendePlaetze(List.of(platzId)));

        // assert
        assertThat(preis).isEqualTo(Geldbetrag.euro(euro, cent));
    }

    @Test
    void ermittlePreis_mehrerePlaetze_summiertEinzelpreise() {
        // arrange — 2x Parkett + 1x Loge bei Standard: 10,50 + 10,50 + 12,50 = 33,50
        var p1 = new PlatzId(new ReiheNummer(1), new PlatzNummer(1));
        var p2 = new PlatzId(new ReiheNummer(1), new PlatzNummer(2));
        var p3 = new PlatzId(new ReiheNummer(1), new PlatzNummer(3));
        var saalplan = new Saalplan(vorstellungId, List.of(
                new Platz(p1, PlatzKategorie.Parkett, false),
                new Platz(p2, PlatzKategorie.Parkett, false),
                new Platz(p3, PlatzKategorie.Loge, false)));
        var vorstellung = vorstellung(VorstellungKategorie.Standard);

        // act
        var preis = preisberechnung.ermittlePreis(vorstellung, saalplan, new ZusammenhaengendePlaetze(List.of(p1, p2, p3)));

        // assert
        assertThat(preis).isEqualTo(Geldbetrag.euro(33, 50));
    }

    private Vorstellung vorstellung(VorstellungKategorie kategorie) {
        return new Vorstellung(
                vorstellungId,
                new Saal("kleiner Saal"),
                new Beginn(LocalDateTime.parse("2025-03-19T14:30")),
                new Film("Guardians of the Lunacy"),
                kategorie);
    }
}
