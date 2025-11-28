package de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf;

import lombok.Value;
import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

@ValueObject
@Value(staticConstructor = "of")
public class Geldbetrag {

    int betrag;
    Waehrung waehrung;

    private Geldbetrag(int betrag, Waehrung waehrung) {
        Assert.isTrue(betrag >= 0, "Betrag muss größer gleich 0 sein.");
        this.betrag = betrag;
        this.waehrung = waehrung;
    }

    public static Geldbetrag euroInCent(int cent) {
        return Geldbetrag.of(cent, Waehrung.EUR);
    }

    public static Geldbetrag euro(int euro, int cent) {
        return Geldbetrag.of(euro * 100 + cent, Waehrung.EUR);
    }

    public Geldbetrag plus(Geldbetrag other) {
        Assert.isTrue(this.waehrung.equals(other.waehrung), "Währungen müssen übereinstimmen");
        return Geldbetrag.of(betrag + other.betrag, this.waehrung);
    }

    public Geldbetrag mal(int anzahl) {
        Assert.isTrue(anzahl >= 0, "Anzahl muss größer gleich 0 sein.");
        return Geldbetrag.of(betrag * anzahl, waehrung);
    }

    public enum Waehrung {
        EUR
    }
}