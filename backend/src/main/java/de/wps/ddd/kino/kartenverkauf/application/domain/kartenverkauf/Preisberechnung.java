package de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf;

import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Vorstellung;
import org.jmolecules.ddd.annotation.Service;
import org.springframework.stereotype.Component;

@Service
@Component
public class Preisberechnung {
    public Geldbetrag ermittlePreis(
            Vorstellung vorstellung,
            Saalplan saalplan,
            ZusammenhaengendePlaetze zusammenhaengendePlaetze) {

        return zusammenhaengendePlaetze.plaetze().stream()
                .map(saalplan::platz)
                .map(platz -> ermittlePreis(vorstellung, platz))
                .reduce(Geldbetrag.euroInCent(0), Geldbetrag::plus);
    }

    private Geldbetrag ermittlePreis(Vorstellung vorstellung, Platz platz) {
        return preisFuerVorstellung(vorstellung)
                .plus(zuschlagPlatzkategorie(platz));
    }

    private Geldbetrag preisFuerVorstellung(Vorstellung vorstellung) {
        return switch (vorstellung.getKategorie()) {
            case Standard -> Geldbetrag.euro(10, 50);
            case PrimeTime -> Geldbetrag.euro(12, 0);
            case Kinderfilm -> Geldbetrag.euro(8, 50);
        };
    }

    private Geldbetrag zuschlagPlatzkategorie(Platz platz) {
        return switch (platz.getKategorie()) {
            case Parkett -> Geldbetrag.euro(0, 0);
            case Loge -> Geldbetrag.euro(2, 0);
        };
    }
}
