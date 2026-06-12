package de.wps.ddd.kino.kartenverkauf.adapters.persistence.repositories;

import de.wps.ddd.kino.common.error.RessourceNichtGefunden;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.AktuelleVorstellungen;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AktuelleVorstellungenIntegrationTest {

    @Autowired
    private AktuelleVorstellungen aktuelleVorstellungen;

    private final VorstellungId vorstellungId =
            new VorstellungId(UUID.fromString("090c173a-3636-4980-865a-1ec859eb4f90"));

    @Test
    void alleVorstellungen_liefertVorstellungen() {
        // act
        var vorstellungen = aktuelleVorstellungen.alleVorstellungen();

        // assert
        assertThat(vorstellungen).isNotEmpty();
    }

    @Test
    void holeVorstellung_bekannteVorstellung_liefertVorstellung() {
        // act
        var vorstellung = aktuelleVorstellungen.holeVorstellung(vorstellungId);

        // assert
        assertThat(vorstellung.getId()).isEqualTo(vorstellungId);
        assertThat(vorstellung.getFilm().name()).isEqualTo("The Fast and the Curious");
        assertThat(vorstellung.getSaal().name()).isEqualTo("kleiner Saal");
    }

    @Test
    void holeVorstellung_unbekannteVorstellung_wirftRessourceNichtGefunden() {
        // arrange
        var unbekannteVorstellungId = new VorstellungId(UUID.randomUUID());

        // act / assert
        assertThatThrownBy(() -> aktuelleVorstellungen.holeVorstellung(unbekannteVorstellungId))
                .isInstanceOf(RessourceNichtGefunden.class)
                .hasMessageContaining("existiert nicht");
    }
}
