package de.wps.ddd.kino.filmauswahl.web;

import de.wps.ddd.kino.filmauswahl.data.Film;
import de.wps.ddd.kino.filmauswahl.data.Saal;
import de.wps.ddd.kino.filmauswahl.data.Vorstellung;
import de.wps.ddd.kino.filmauswahl.service.ProgrammService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProgrammController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProgrammControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProgrammService programmService;

    @Test
    void holeVorstellungenFuerTag_liefertFilme() throws Exception {
        // arrange
        var saal = new Saal(1L, "Großer Saal");
        var film = new Film(
                1L,
                "Guardians of the Lunacy",
                120,
                "https://example.com/poster/guardians-of-the-lunacy.jpg",
                12,
                "Eine spektakuläre Weltraum-Komödie um eine chaotische Crew.",
                "Science-Fiction",
                "Chris Pratt",
                "James Gunn",
                "Deutsch",
                List.of(
                        new Vorstellung(1L, UUID.fromString("11111111-1111-1111-1111-111111111111"), 1L,
                                LocalDateTime.parse("2025-03-19T14:30"), saal),
                        new Vorstellung(2L, UUID.fromString("22222222-2222-2222-2222-222222222222"), 1L,
                                LocalDateTime.parse("2025-03-19T20:30"), saal)));
        when(programmService.holeVorstellungenFuerTag(LocalDate.parse("2025-03-19"))).thenReturn(List.of(film));

        // act / assert
        mockMvc.perform(get("/api/programm").param("datum", "2025-03-19"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].titel").value("Guardians of the Lunacy"))
                .andExpect(jsonPath("$[0].genre").value("Science-Fiction"))
                .andExpect(jsonPath("$[0].fsk").value(12))
                .andExpect(jsonPath("$[0].vorstellungen.length()").value(2))
                .andExpect(jsonPath("$[0].vorstellungen[0].saal").value("Großer Saal"))
                .andExpect(jsonPath("$[0].vorstellungen[0].beginn").value("2025-03-19T14:30:00"));
    }
}
