package de.wps.ddd.kino.kartenverkauf.adapters.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.VorstellungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Beginn;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Film;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Saal;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungKategorie;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VorstellungDtoMapperTest {

    private final VorstellungDtoMapper mapper = new VorstellungDtoMapper();

    @Test
    void toDto_mapptVorstellungAufDto() {
        // arrange
        var uuid = UUID.fromString("090c173a-3636-4980-865a-1ec859eb4f90");
        var vorstellung = new Vorstellung(
                new VorstellungId(uuid),
                new Saal("kleiner Saal"),
                new Beginn(LocalDateTime.parse("2025-03-23T14:30")),
                new Film("The Fast and the Curious"),
                VorstellungKategorie.Standard);

        // act
        var dto = mapper.toDto(vorstellung);

        // assert
        assertThat(dto.uuid()).isEqualTo(uuid);
        assertThat(dto.beginn()).isEqualTo("2025-03-23T14:30");
        assertThat(dto.saal()).isEqualTo("kleiner Saal");
        assertThat(dto.film()).isEqualTo("The Fast and the Curious");
    }
}
