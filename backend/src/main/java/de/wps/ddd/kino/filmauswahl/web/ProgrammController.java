package de.wps.ddd.kino.filmauswahl.web;

import de.wps.ddd.kino.filmauswahl.data.Film;
import de.wps.ddd.kino.filmauswahl.service.ProgrammService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/programm")
@RequiredArgsConstructor
public class ProgrammController {

    private final ProgrammService programmService;

    @GetMapping()
    public List<Film> holeVorstellungenFuerTag(@RequestParam LocalDate datum) {
        return programmService.holeVorstellungenFuerTag(datum);
    }
}
