package de.wps.ddd.kino.common.error;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RessourceNichtGefundenTest {

    @Test
    void wenn_bedingungErfuellt_wirftRessourceNichtGefunden() {
        // arrange
        var meldung = "Vorstellung nicht gefunden.";

        // act / assert
        assertThatThrownBy(() -> RessourceNichtGefunden.wenn(true, meldung))
                .isInstanceOf(RessourceNichtGefunden.class)
                .hasMessage(meldung);
    }

    @Test
    void wenn_bedingungNichtErfuellt_wirftNicht() {
        // act / assert
        assertThatCode(() -> RessourceNichtGefunden.wenn(false, "darf nicht geworfen werden"))
                .doesNotThrowAnyException();
    }
}
