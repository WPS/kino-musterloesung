package de.wps.ddd.kino.kartenverkauf.adapters.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers.ZahlungsvorgangEntityMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.ZahlungsvorgangEntity;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsstatus;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsvorgang;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ZahlungsvorgangEntityMapperTest {

    private final ZahlungsvorgangEntityMapper mapper = new ZahlungsvorgangEntityMapper();

    private final Auftragsnummer auftragsnummer =
            new Auftragsnummer(UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74"));

    @Test
    void toEntity() {
        // arrange
        var zahlungsvorgang = new Zahlungsvorgang(auftragsnummer, Zahlungsstatus.Eingegangen);

        // act
        var entity = mapper.toEntity(zahlungsvorgang);

        // assert
        assertThat(entity.getAuftragsnummer()).isEqualTo(auftragsnummer.nummer());
        assertThat(entity.getStatus()).isEqualTo("Eingegangen");
    }

    @Test
    void toDomain() {
        // arrange
        var entity = new ZahlungsvorgangEntity(auftragsnummer.nummer(), "Eingegangen");

        // act
        var zahlungsvorgang = mapper.toDomain(entity);

        // assert
        assertThat(zahlungsvorgang.getAuftragsnummer()).isEqualTo(auftragsnummer);
        assertThat(zahlungsvorgang.getStatus()).isEqualTo(Zahlungsstatus.Eingegangen);
    }
}
