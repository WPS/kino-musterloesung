package de.wps.ddd.kino.common.web;

import de.wps.ddd.kino.common.error.GeschaeftsregelVerletzt;
import de.wps.ddd.kino.common.error.RessourceNichtGefunden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RessourceNichtGefunden.class)
    public ProblemDetail handleNichtGefunden(RessourceNichtGefunden ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Ressource nicht gefunden");
        return problem;
    }

    @ExceptionHandler(GeschaeftsregelVerletzt.class)
    public ProblemDetail handleGeschaeftsregelVerletzt(GeschaeftsregelVerletzt ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Geschäftsregel verletzt");
        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleUngueltigeAnfrage(IllegalArgumentException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Ungültige Anfrage");
        return problem;
    }
}
