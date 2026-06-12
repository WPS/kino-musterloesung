package de.wps.ddd.kino.common.error;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeschaeftsregelVerletztTest {

    @Test
    void wenn_bedingungErfuellt_wirftGeschaeftsregelVerletzt() {
        // arrange
        var meldung = "Nur ausstehende Zahlungsvorgänge können abgeschlossen werden.";

        // act / assert
        assertThatThrownBy(() -> GeschaeftsregelVerletzt.wenn(true, meldung))
                .isInstanceOf(GeschaeftsregelVerletzt.class)
                .hasMessage(meldung);
    }

    @Test
    void wenn_bedingungNichtErfuellt_wirftNicht() {
        // act / assert
        assertThatCode(() -> GeschaeftsregelVerletzt.wenn(false, "darf nicht geworfen werden"))
                .doesNotThrowAnyException();
    }
}
