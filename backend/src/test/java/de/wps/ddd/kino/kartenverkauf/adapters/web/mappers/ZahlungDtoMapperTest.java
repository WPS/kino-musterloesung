package de.wps.ddd.kino.kartenverkauf.adapters.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.ZahlungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsstatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ZahlungDtoMapperTest {

    private final ZahlungDtoMapper mapper = new ZahlungDtoMapper();

    @Test
    void toDto_geldbetrag_mapptBetragUndWaehrung() {
        // act
        var dto = mapper.toDto(Geldbetrag.euro(10, 50));

        // assert
        assertThat(dto.betrag()).isEqualTo(1050);
        assertThat(dto.waehrung()).isEqualTo("EUR");
    }

    @Test
    void toDto_zahlungsstatus_mapptStatusAlsString() {
        // act
        var dto = mapper.toDto(Zahlungsstatus.Eingegangen);

        // assert
        assertThat(dto.status()).isEqualTo("Eingegangen");
    }
}
