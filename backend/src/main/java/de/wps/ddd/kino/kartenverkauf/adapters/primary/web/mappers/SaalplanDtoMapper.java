package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.PlatzIdDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.ZusammenhaengendePlaetzeDto;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SaalplanDtoMapper {

    public SaalplanDto toDto(Saalplan saalplan) {
        var plaetze = saalplan.getPlaetze();

        var reihenzahl = plaetze.size();
        var ersteReihe = plaetze.values().stream().findFirst();
        var platzAnzahl = ersteReihe.map(Map::size).orElse(0);
        var platzbelegungen = new PlatzDto[reihenzahl][platzAnzahl];

        plaetze.forEach((reiheNr, plaetzeListe) -> plaetzeListe.forEach((platzNr, platz) ->
                platzbelegungen[reiheNr.nummer() - 1][platzNr.nummer() - 1] = toDto(platz)
        ));

        return new SaalplanDto(platzbelegungen);
    }

    public ZusammenhaengendePlaetzeDto toDto(ZusammenhaengendePlaetze plaetze) {
        return new ZusammenhaengendePlaetzeDto(plaetze.plaetze().stream().map(this::toDto).toList());
    }

    public ZusammenhaengendePlaetze toDomain(ZusammenhaengendePlaetzeDto plaetze) {
        return new ZusammenhaengendePlaetze(plaetze.plaetze().stream().map(this::toDomain).toList());
    }

    private PlatzDto toDto(Platz platz) {
        return new PlatzDto(
                platz.getId().reihe().nummer(),
                platz.getId().platz().nummer(),
                platz.istFrei()
        );
    }

    private PlatzIdDto toDto(PlatzId platzId) {
        return new PlatzIdDto(platzId.reihe().nummer(), platzId.platz().nummer());
    }

    private PlatzId toDomain(PlatzIdDto platzId) {
        return new PlatzId(new ReiheNummer(platzId.reihe()), new PlatzNummer(platzId.platz()));
    }
}
