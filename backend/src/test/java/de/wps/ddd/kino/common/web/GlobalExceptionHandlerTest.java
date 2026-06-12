package de.wps.ddd.kino.common.web;

import de.wps.ddd.kino.common.error.GeschaeftsregelVerletzt;
import de.wps.ddd.kino.common.error.RessourceNichtGefunden;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNichtGefunden_liefertProblemDetailMit404() {
        // arrange
        var ex = catchThrowableOfType(RessourceNichtGefunden.class,
                () -> RessourceNichtGefunden.wenn(true, "Vorstellung existiert nicht"));

        // act
        var problem = handler.handleNichtGefunden(ex);

        // assert
        assertThat(problem.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(problem.getTitle()).isEqualTo("Ressource nicht gefunden");
        assertThat(problem.getDetail()).isEqualTo("Vorstellung existiert nicht");
    }

    @Test
    void handleGeschaeftsregelVerletzt_liefertProblemDetailMit409() {
        // arrange
        var ex = catchThrowableOfType(GeschaeftsregelVerletzt.class,
                () -> GeschaeftsregelVerletzt.wenn(true, "Zahlung ist noch nicht eingegangen"));

        // act
        var problem = handler.handleGeschaeftsregelVerletzt(ex);

        // assert
        assertThat(problem.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(problem.getTitle()).isEqualTo("Geschäftsregel verletzt");
        assertThat(problem.getDetail()).isEqualTo("Zahlung ist noch nicht eingegangen");
    }

    @Test
    void handleUngueltigeAnfrage_liefertProblemDetailMit400() {
        // arrange
        var ex = new IllegalArgumentException("Platzanzahl darf nicht negativ sein.");

        // act
        var problem = handler.handleUngueltigeAnfrage(ex);

        // assert
        assertThat(problem.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(problem.getTitle()).isEqualTo("Ungültige Anfrage");
        assertThat(problem.getDetail()).isEqualTo("Platzanzahl darf nicht negativ sein.");
    }
}
