package de.wps.ddd.kino.filmauswahl.service.domain;

import de.wps.ddd.kino.filmauswahl.service.ProgrammService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ProgrammServiceTest {

    @Autowired
    private ProgrammService programmService;

    @Test
    void holeVorstellungenFuerTag() {
        var datum = LocalDate.parse("2025-03-19");
        var filme = programmService.holeVorstellungenFuerTag(datum);
        assertThat(filme).isNotNull();
        assertThat(filme).hasSize(3);
        var film1 = filme.get(0);
        var film2 = filme.get(1);
        var film3 = filme.get(2);
        assertThat(film1.getTitel()).isEqualTo("Guardians of the Lunacy");
        assertThat(film2.getTitel()).isEqualTo("The Fast and the Curious");
        assertThat(film3.getTitel()).isEqualTo("Clown Wars");
        assertThat(film1.getVorstellungen()).hasSize(1);
        assertThat(film2.getVorstellungen()).hasSize(2);
        assertThat(film3.getVorstellungen()).hasSize(1);
        assertThat(film1.getVorstellungen().get(0).getBeginn()).isEqualTo("2025-03-19T14:30:00");
        assertThat(film2.getVorstellungen().get(0).getBeginn()).isEqualTo("2025-03-19T15:00:00");
        assertThat(film2.getVorstellungen().get(1).getBeginn()).isEqualTo("2025-03-19T20:30:00");
        assertThat(film3.getVorstellungen().get(0).getBeginn()).isEqualTo("2025-03-19T22:30:00");
    }
}
