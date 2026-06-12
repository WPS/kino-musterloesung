package de.wps.ddd.kino.kartenverkauf.adapters.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers.VorstellungEntityMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.VorstellungEntity;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Beginn;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Film;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Saal;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungKategorie;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VorstellungEntityMapperTest {

    private final VorstellungEntityMapper mapper = new VorstellungEntityMapper();

    @Test
    void toDomain() {
        // arrange
        var uuid = UUID.fromString("090c173a-3636-4980-865a-1ec859eb4f90");
        var beginn = LocalDateTime.parse("2025-03-23T14:30");
        var entity = new VorstellungEntity(uuid, beginn, "kleiner Saal", "The Fast and the Curious", "Standard");

        // act
        var vorstellung = mapper.toDomain(entity);

        // assert
        assertThat(vorstellung.getId()).isEqualTo(new VorstellungId(uuid));
        assertThat(vorstellung.getBeginn()).isEqualTo(new Beginn(beginn));
        assertThat(vorstellung.getSaal()).isEqualTo(new Saal("kleiner Saal"));
        assertThat(vorstellung.getFilm()).isEqualTo(new Film("The Fast and the Curious"));
        assertThat(vorstellung.getKategorie()).isEqualTo(VorstellungKategorie.Standard);
    }
}
