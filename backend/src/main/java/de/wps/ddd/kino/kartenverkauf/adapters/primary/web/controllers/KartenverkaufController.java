package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.controllers;

import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.KartenDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.SaalplanDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.VorstellungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.ZahlungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.GeldbetragDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.KinokarteDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.PreisanfrageDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.VorstellungDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.ZusammenhaengendePlaetzeDto;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platzanzahl;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.BerechneGesamtpreis;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.ErstelleKinokarten;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.HoleGewaehlteVorstellung;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.HoleSaalplan;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.SucheZusammenhaengendePlaetze;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/kartenverkauf")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
class KartenverkaufController {

    private final VorstellungDtoMapper vorstellungMapper;
    private final SaalplanDtoMapper saalplanMapper;
    private final ZahlungDtoMapper zahlungMapper;
    private final KartenDtoMapper kartenMapper;

    private final HoleGewaehlteVorstellung holeGewaehlteVorstellung;
    private final HoleSaalplan holeSaalplan;
    private final SucheZusammenhaengendePlaetze sucheZusammenhaengendePlaetze;
    private final BerechneGesamtpreis berechneGesamtpreis;
    private final ErstelleKinokarten erstelleKinokarten;

    @GetMapping("/vorstellungen/{id}")
    public VorstellungDto holeGewaehlteVorstellung(@PathVariable UUID id) {
        var vorstellungId = new VorstellungId(id);
        var vorstellung = holeGewaehlteVorstellung.fuer(vorstellungId);
        return vorstellungMapper.toDto(vorstellung);
    }

    @GetMapping("/saalplaene/{id}")
    public SaalplanDto holeSaalplan(@PathVariable UUID id) {
        var vorstellungId = new VorstellungId(id);
        var saalplan = holeSaalplan.fuer(vorstellungId);
        return saalplanMapper.toDto(saalplan);
    }

    @GetMapping("/saalplaene/{id}/suche-zusammenhaengende-plaetze")
    public ZusammenhaengendePlaetzeDto sucheZusammenhaengendePlatze(@PathVariable UUID id, @RequestParam int platzanzahl) {
        var vorstellungId = new VorstellungId(id);
        var anzahl = new Platzanzahl(platzanzahl);
        var plaetze = sucheZusammenhaengendePlaetze.fuer(vorstellungId, anzahl);
        return saalplanMapper.toDto(plaetze);
    }

    @PostMapping("/preisanfrage")
    public GeldbetragDto berechneGesamtpreis(@RequestBody PreisanfrageDto preisanfrageDto) {
        var vorstellungId = new VorstellungId(preisanfrageDto.vorstellungId());
        var gewaehltePlaetze = saalplanMapper.toDomain(preisanfrageDto.plaetze());
        var gesamtpreis = berechneGesamtpreis.fuer(vorstellungId, gewaehltePlaetze);
        return zahlungMapper.toDto(gesamtpreis);
    }

    @PostMapping("/kinokarten/{id}")
    public List<KinokarteDto> erstelleKinokarten(@PathVariable UUID id, @RequestBody PreisanfrageDto preisanfrageDto) {
        var auftragsnummer = new Auftragsnummer(id);
        var vorstellungId = new VorstellungId(preisanfrageDto.vorstellungId()); // TODO sollte aus Auftragsnummer hervorgehen. get statt post request
        var gewaehltePlaetze = saalplanMapper.toDomain(preisanfrageDto.plaetze());
        var kinokarten = erstelleKinokarten.fuer(auftragsnummer, vorstellungId, gewaehltePlaetze);
        return kartenMapper.toDto(kinokarten);
    }
}
