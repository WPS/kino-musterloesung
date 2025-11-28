package de.wps.ddd.kino.kartenverkauf.adapters.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.SaalplanDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.PlatzIdDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.ZusammenhaengendePlaetzeDto;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzKategorie;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SaalplanDtoMapperTest {

    private final SaalplanDtoMapper mapper = new SaalplanDtoMapper();

    private final ReiheNummer reiheNr = new ReiheNummer(3);
    private final PlatzNummer platzNr = new PlatzNummer(2);
    private final PlatzId platzId = new PlatzId(reiheNr, platzNr);

    @Test
    void saalplanToSaalplanDto() {
        // arrange

        var reiheNr1 = new ReiheNummer(1);
        var reiheNr2 = new ReiheNummer(2);

        var platzNr1 = new PlatzNummer(1);
        var platzNr2 = new PlatzNummer(2);

        var platzId1 = new PlatzId(reiheNr1, platzNr1);
        var platzId2 = new PlatzId(reiheNr1, platzNr2);
        var platzId3 = new PlatzId(reiheNr2, platzNr1);
        var platzId4 = new PlatzId(reiheNr2, platzNr2);

        var kategorie = PlatzKategorie.Loge;

        var reihe1_platz1 = new Platz(platzId1, kategorie, false);
        var reihe1_platz2 = new Platz(platzId2, kategorie, true);
        var reihe2_platz1 = new Platz(platzId3, kategorie, true);
        var reihe2_platz2 = new Platz(platzId4, kategorie, false);

        var plaetze = List.of(reihe1_platz1, reihe1_platz2, reihe2_platz1, reihe2_platz2);

        var reihe1_platz1_dto = new PlatzDto(1, 1, true);
        var reihe1_platz2_dto = new PlatzDto(1, 2, false);
        var reihe2_platz1_dto = new PlatzDto(2, 1, false);
        var reihe2_platz2_dto = new PlatzDto(2, 2, true);

        var vorstellungId = new VorstellungId(UUID.randomUUID());
        var saalplan = new Saalplan(vorstellungId, plaetze);
        PlatzDto[][] expectedPlatzDtos = {
                {reihe1_platz1_dto, reihe1_platz2_dto},
                {reihe2_platz1_dto, reihe2_platz2_dto},
        };

        // act
        var saalplanDto = mapper.toDto(saalplan);

        // assert
        assertThat(saalplanDto.plaetze()).isEqualTo(expectedPlatzDtos);
    }

    @Test
    void zusammenhaengendePlaetzeToDto() {
        var zusammenhaengendePlaetze = new ZusammenhaengendePlaetze(List.of(platzId));

        var dto = mapper.toDto(zusammenhaengendePlaetze);

        assertThat(dto.plaetze()).hasSize(1);
        var platzDto = dto.plaetze().getFirst();
        assertThat(platzDto.reihe()).isEqualTo(reiheNr.nummer());
        assertThat(platzDto.platz()).isEqualTo(platzNr.nummer());
    }

    @Test
    void zusammenhaengendePlaetzeToDomain() {
        var zusammenhaengendePlaetzeDto =
                new ZusammenhaengendePlaetzeDto(List.of(new PlatzIdDto(reiheNr.nummer(), platzNr.nummer())));

        var plaetze = mapper.toDomain(zusammenhaengendePlaetzeDto);

        assertThat(plaetze.plaetze()).hasSize(1);
        var platz = plaetze.plaetze().getFirst();
        assertThat(platz.reihe()).isEqualTo(reiheNr);
        assertThat(platz.platz()).isEqualTo(platzNr);
    }
}
