package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.VorstellungDto;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Vorstellung;
import org.springframework.stereotype.Component;

@Component
public class VorstellungDtoMapper {

    public VorstellungDto toDto(Vorstellung vorstellung) {
        return new VorstellungDto(
                vorstellung.getId().uuid(),
                vorstellung.getBeginn().zeitpunkt().toString(),
                vorstellung.getSaal().name(),
                vorstellung.getFilm().name()
        );
    }

}
