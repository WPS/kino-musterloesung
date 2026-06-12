package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.controllers;

import de.wps.ddd.kino.common.web.GlobalExceptionHandler;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.SaalplanDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.ZahlungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.ZahlungEingegangen;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsstatus;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.BerechneGesamtpreis;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.Zahlung;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ZahlungController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({SaalplanDtoMapper.class, ZahlungDtoMapper.class, GlobalExceptionHandler.class})
class ZahlungControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BerechneGesamtpreis berechneGesamtpreis;
    @MockitoBean
    private Zahlung zahlung;

    private static final UUID VORSTELLUNG_ID = UUID.fromString("090c173a-3636-4980-865a-1ec859eb4f90");
    private static final UUID AUFTRAGSNUMMER = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Test
    void starteZahlungsvorgang_liefertZahlungsvorgangDto() throws Exception {
        // arrange
        when(berechneGesamtpreis.fuer(any(), any())).thenReturn(Geldbetrag.euro(50, 0));
        when(zahlung.starteZahlungsvorgang(any(), any(), any())).thenReturn(new Auftragsnummer(AUFTRAGSNUMMER));
        var body = """
                {"vorstellungId":"%s","plaetze":{"plaetze":[{"reihe":4,"platz":1}]}}""".formatted(VORSTELLUNG_ID);

        // act / assert
        mockMvc.perform(post("/api/kartenverkauf/zahlung").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auftragsnummer").value(AUFTRAGSNUMMER.toString()))
                .andExpect(jsonPath("$.betrag.betrag").value(5000));
    }

    @Test
    void zahlungStatus_liefertStatusDto() throws Exception {
        // arrange
        when(zahlung.status(new Auftragsnummer(AUFTRAGSNUMMER))).thenReturn(Zahlungsstatus.Eingegangen);

        // act / assert
        mockMvc.perform(get("/api/kartenverkauf/zahlung/{id}/status", AUFTRAGSNUMMER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Eingegangen"));
    }

    @Test
    void bestaetigeZahlungseingang_verarbeitetEingangUndLiefertStatus() throws Exception {
        // arrange
        when(zahlung.status(new Auftragsnummer(AUFTRAGSNUMMER))).thenReturn(Zahlungsstatus.Eingegangen);

        // act
        mockMvc.perform(post("/api/kartenverkauf/zahlung/{id}/bestaetigen", AUFTRAGSNUMMER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Eingegangen"));

        // assert
        verify(zahlung).verarbeite(new ZahlungEingegangen(new Auftragsnummer(AUFTRAGSNUMMER)));
    }
}
