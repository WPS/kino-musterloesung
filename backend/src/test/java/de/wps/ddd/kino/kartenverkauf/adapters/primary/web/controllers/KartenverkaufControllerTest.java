package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.controllers;

import de.wps.ddd.kino.common.web.GlobalExceptionHandler;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.KartenDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.SaalplanDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.VorstellungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.ZahlungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Kinokarte;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzKategorie;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platzanzahl;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Beginn;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Film;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Saal;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungKategorie;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.BerechneGesamtpreis;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.ErstelleKinokarten;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.HoleGewaehlteVorstellung;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.HoleSaalplan;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.SucheZusammenhaengendePlaetze;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = KartenverkaufController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({VorstellungDtoMapper.class, SaalplanDtoMapper.class, ZahlungDtoMapper.class, KartenDtoMapper.class,
        GlobalExceptionHandler.class})
class KartenverkaufControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String PREISANFRAGE_JSON = """
            {"vorstellungId":"090c173a-3636-4980-865a-1ec859eb4f90",
             "plaetze":{"plaetze":[{"reihe":4,"platz":1},{"reihe":4,"platz":2}]}}""";

    @MockitoBean
    private HoleGewaehlteVorstellung holeGewaehlteVorstellung;
    @MockitoBean
    private HoleSaalplan holeSaalplan;
    @MockitoBean
    private SucheZusammenhaengendePlaetze sucheZusammenhaengendePlaetze;
    @MockitoBean
    private BerechneGesamtpreis berechneGesamtpreis;
    @MockitoBean
    private ErstelleKinokarten erstelleKinokarten;

    private static final UUID ID = UUID.fromString("090c173a-3636-4980-865a-1ec859eb4f90");

    @Test
    void holeGewaehlteVorstellung_liefertVorstellungDto() throws Exception {
        // arrange
        when(holeGewaehlteVorstellung.fuer(new VorstellungId(ID))).thenReturn(vorstellung());

        // act / assert
        mockMvc.perform(get("/api/kartenverkauf/vorstellungen/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.film").value("The Fast and the Curious"))
                .andExpect(jsonPath("$.saal").value("kleiner Saal"));
    }

    @Test
    void holeSaalplan_liefertSaalplanDto() throws Exception {
        // arrange
        var saalplan = new Saalplan(new VorstellungId(ID), List.of(
                new Platz(platzId(1, 1), PlatzKategorie.Parkett, false)));
        when(holeSaalplan.fuer(new VorstellungId(ID))).thenReturn(saalplan);

        // act / assert
        mockMvc.perform(get("/api/kartenverkauf/saalplaene/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plaetze[0][0].reihe").value(1))
                .andExpect(jsonPath("$.plaetze[0][0].istFrei").value(true));
    }

    @Test
    void sucheZusammenhaengendePlaetze_liefertGefundenePlaetze() throws Exception {
        // arrange
        when(sucheZusammenhaengendePlaetze.fuer(new VorstellungId(ID), new Platzanzahl(2)))
                .thenReturn(new ZusammenhaengendePlaetze(List.of(platzId(4, 1), platzId(4, 2))));

        // act / assert
        mockMvc.perform(get("/api/kartenverkauf/saalplaene/{id}/suche-zusammenhaengende-plaetze", ID)
                        .param("platzanzahl", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plaetze.length()").value(2))
                .andExpect(jsonPath("$.plaetze[0].reihe").value(4))
                .andExpect(jsonPath("$.plaetze[0].platz").value(1));
    }

    @Test
    void sucheZusammenhaengendePlaetze_negativePlatzanzahl_liefert400() throws Exception {
        // act / assert
        mockMvc.perform(get("/api/kartenverkauf/saalplaene/{id}/suche-zusammenhaengende-plaetze", ID)
                        .param("platzanzahl", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void berechneGesamtpreis_liefertGeldbetragDto() throws Exception {
        // arrange
        when(berechneGesamtpreis.fuer(eq(new VorstellungId(ID)), any())).thenReturn(Geldbetrag.euro(50, 0));
        var body = PREISANFRAGE_JSON;

        // act / assert
        mockMvc.perform(post("/api/kartenverkauf/preisanfrage").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.betrag").value(5000))
                .andExpect(jsonPath("$.waehrung").value("EUR"));
    }

    @Test
    void erstelleKinokarten_liefertKinokartenDtos() throws Exception {
        // arrange
        when(erstelleKinokarten.fuer(any(), any(), any())).thenReturn(List.of(
                kinokarte(4, 1), kinokarte(4, 2)));
        var body = PREISANFRAGE_JSON;

        // act / assert
        mockMvc.perform(post("/api/kartenverkauf/kinokarten/{id}", ID).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].reihe").value(4))
                .andExpect(jsonPath("$[0].platz").value(1));
    }

    private static Vorstellung vorstellung() {
        return new Vorstellung(
                new VorstellungId(ID),
                new Saal("kleiner Saal"),
                new Beginn(LocalDateTime.parse("2025-03-23T14:30")),
                new Film("The Fast and the Curious"),
                VorstellungKategorie.Standard);
    }

    private static Kinokarte kinokarte(int reihe, int platz) {
        return new Kinokarte(
                null,
                new Film("The Fast and the Curious"),
                new Beginn(LocalDateTime.parse("2025-03-23T14:30")),
                new Saal("kleiner Saal"),
                new ReiheNummer(reihe),
                new PlatzNummer(platz));
    }

    private static PlatzId platzId(int reihe, int platz) {
        return new PlatzId(new ReiheNummer(reihe), new PlatzNummer(platz));
    }
}
