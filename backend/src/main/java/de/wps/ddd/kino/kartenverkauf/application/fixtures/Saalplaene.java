package de.wps.ddd.kino.kartenverkauf.application.fixtures;

import de.wps.ddd.kino.common.fixtures.Fixture;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzKategorie;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.AktuelleVorstellungen;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.SaalplanStapel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class Saalplaene implements Fixture {

    private final AktuelleVorstellungen aktuelleVorstellungen;
    private final SaalplanStapel saalplanStapel;

    @Transactional
    @Override
    public void install() {
        log.info("Erzeuge Saalpläne...");

        var random = new Random(42);

        var vorstellungen = aktuelleVorstellungen.alleVorstellungen();
        for (var vorstellung : vorstellungen) {
            log.info("Erzeuge Saalplan für Vorstellung: {}", vorstellung);

            var reihen = switch (vorstellung.getSaal().name()) {
                case "kleiner Saal" -> 4;
                case "großer Saal" -> 6;
                default -> throw new IllegalStateException("Unexpected value: " + vorstellung.getSaal());
            };

            var spalten = switch (vorstellung.getSaal().name()) {
                case "kleiner Saal" -> 8;
                case "großer Saal" -> 12;
                default -> throw new IllegalStateException("Unexpected value: " + vorstellung.getSaal());
            };

            var plaetze = new ArrayList<Platz>(reihen * spalten);
            for (int reihe = 1; reihe <= reihen; reihe++) {
                var kategorie = reihe <= reihen / 2 ? PlatzKategorie.Parkett : PlatzKategorie.Loge;
                for (int spalte = 1; spalte <= spalten; spalte++) {
                    var istVerkauft = random.nextInt(4) == 0;
                    var platz = new Platz(new PlatzId(new ReiheNummer(reihe), new PlatzNummer(spalte)), kategorie, istVerkauft);
                    plaetze.add(platz);
                }
            }

            var saalplan = new Saalplan(vorstellung.getId(), plaetze);

            saalplanStapel.legeZurueck(saalplan);
        }

        log.info("Saalpläne erzeugt: {}", vorstellungen.size());
    }
}
