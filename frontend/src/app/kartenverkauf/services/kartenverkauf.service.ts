import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {
  Geldbetrag,
  Kinokarte,
  Preisanfrage,
  Saalplan,
  Vorstellung,
  Zahlungsstatus,
  Zahlungsvorgang,
  ZusammenhaengendePlaetze
} from '../dtos/kartenverkauf';

@Injectable({
  providedIn: 'root'
})
export class KartenverkaufService {
  private http = inject(HttpClient);

  private kartenverkaufUrl: string = '/api/kartenverkauf';

  public holeVorstellung(vorstellungId: string): Observable<Vorstellung> {
    return this.http.get<Vorstellung>(`${this.kartenverkaufUrl}/vorstellungen/${vorstellungId}`);
  }

  public holeSaalplan(vorstellungId: string): Observable<Saalplan> {
    return this.http.get<Saalplan>(`${this.kartenverkaufUrl}/saalplaene/${vorstellungId}`);
  }

  public sucheZusammenhaengendePlaetze(vorstellungId: string, platzanzahl: number): Observable<ZusammenhaengendePlaetze> {
    return this.http.get<ZusammenhaengendePlaetze>(`${this.kartenverkaufUrl}/saalplaene/${vorstellungId}/suche-zusammenhaengende-plaetze`, {
      params: {
        platzanzahl,
      }
    })
  }

  public ermittlePreis(vorstellungId: string, plaetze: ZusammenhaengendePlaetze): Observable<Geldbetrag> {
    const preisanfrage: Preisanfrage = {vorstellungId, plaetze};
    return this.http.post<Geldbetrag>(`${this.kartenverkaufUrl}/preisanfrage`, preisanfrage);
  }

  public starteZahlungsvorgang(vorstellungId: string, plaetze: ZusammenhaengendePlaetze): Observable<Zahlungsvorgang> {
    const preisanfrage: Preisanfrage = {vorstellungId, plaetze};
    return this.http.post<Zahlungsvorgang>(`${this.kartenverkaufUrl}/zahlung`, preisanfrage);
  }

  public bestaetigeZahlung(auftragsnummer: string): Observable<Zahlungsstatus> {
    return this.http.post<Zahlungsstatus>(`${this.kartenverkaufUrl}/zahlung/${auftragsnummer}/bestaetigen`, {});
  }

  public erstelleKinokarten(auftragsnummer: string, vorstellungId: string, plaetze: ZusammenhaengendePlaetze): Observable<Kinokarte[]> {
    const preisanfrage: Preisanfrage = {vorstellungId, plaetze};
    return this.http.post<Kinokarte[]>(this.kartenverkaufUrl + '/kinokarten/' + auftragsnummer, preisanfrage);
  }
}
