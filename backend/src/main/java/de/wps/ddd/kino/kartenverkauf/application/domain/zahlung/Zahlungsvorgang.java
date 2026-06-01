package de.wps.ddd.kino.kartenverkauf.application.domain.zahlung;

import de.wps.ddd.kino.common.error.GeschaeftsregelVerletzt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

@AggregateRoot
@Getter
@AllArgsConstructor
public class Zahlungsvorgang {

    @Identity
    private final Auftragsnummer auftragsnummer;
    private Zahlungsstatus status;

    public static Zahlungsvorgang starteZahlung() {
        return new Zahlungsvorgang(Auftragsnummer.neueAuftragsnummer(), Zahlungsstatus.Ausstehend);
    }

    public void zahlungEingegangen() {
        GeschaeftsregelVerletzt.throwIf(this.status != Zahlungsstatus.Ausstehend, "Nur ausstehende Zahlungsvorgänge können abgeschlossen werden.");
        this.status = Zahlungsstatus.Eingegangen;
    }

    public void zahlungAbgebrochen() {
        GeschaeftsregelVerletzt.throwIf(this.status != Zahlungsstatus.Ausstehend, "Nur ausstehende Zahlungsvorgänge können abgebrochen werden.");
        this.status = Zahlungsstatus.Abgebrochen;
    }
}
