package de.wps.ddd.kino.kartenverkauf.adapters.persistence.repositories;


import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzKategorie;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.SaalplanStapel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SaalplanStapelTest {

    @Autowired
    private SaalplanStapel saalplanStapel;

    private final VorstellungId vorstellungId = new VorstellungId(UUID.fromString("f142de00-f3ec-4a42-9493-d406b3062b4a"));

    @Test
    public void holeSaalplan() {
        // act
        var saalplan = saalplanStapel.holeSaalplan(vorstellungId);

        // assert
        assertThat(saalplan).isNotNull();
        assertThat(saalplan.getVorstellungId()).isEqualTo(vorstellungId);
        int reihenAnzahl = 4;
        int platzAnzahlInReihe = 8;
        List<ReiheNummer> reihen = saalplan.getPlaetze().keySet().stream().toList();
        assertThat(reihen).hasSize(reihenAnzahl);
        saalplan.getPlaetze().forEach((reihe, plaetzeListe) -> assertThat(plaetzeListe).hasSize(platzAnzahlInReihe));
    }

    @Test
    public void legeZurueck_geaenderterSaalplan() {
        // arrange
        var saalplan = saalplanStapel.holeSaalplan(vorstellungId);
        var platzId = new PlatzId(new ReiheNummer(2), new PlatzNummer(3));
        assertThat(saalplan.platz(platzId).istVerkauft()).isFalse();

        // act
        saalplan.platz(platzId).markiereAlsVerkauft();
        saalplanStapel.legeZurueck(saalplan);

        // assert
        var geaenderterSaalplan = saalplanStapel.holeSaalplan(vorstellungId);
        assertThat(geaenderterSaalplan.platz(platzId).istVerkauft()).isTrue();
    }

    @Test
    public void legeZurueck_neuerSaalplan() {
        // arrange
        var vorstellungId = new VorstellungId(UUID.randomUUID());
        var platzId = new PlatzId(new ReiheNummer(1), new PlatzNummer(1));
        var platzKategorie = PlatzKategorie.Loge;
        var saalplan = new Saalplan(vorstellungId, List.of(
                new Platz(platzId, platzKategorie, true)));

        // act
        saalplanStapel.legeZurueck(saalplan);

        // assert
        var neuerSaalplan = saalplanStapel.holeSaalplan(vorstellungId);
        assertThat(neuerSaalplan).isNotNull();
        assertThat(neuerSaalplan.platz(platzId).getKategorie()).isEqualTo(PlatzKategorie.Loge);
        assertThat(neuerSaalplan.platz(platzId).istVerkauft()).isTrue();
    }
}