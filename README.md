# KinoSoft - Eine Fallstudie für Domain-Driven Design (DDD)

KinoSoft ist eine Beispielanwendung, die für die [DDD-Schulung](https://www.wps.de/schulungen/isaqb/ddd)
der [WPS - Workplace Solutions GmbH](https://www.wps.de) entwickelt wurde. Die Anwendung demonstriert die
Umsetzung von DDD-Prinzipien in einer Full-Stack-Webanwendung.

Diese Implementierung ist nur eine von vielen möglichen. Unterschiedliche Annahmen, Randbedingungen, (Geschäfts-)Ziele,
und Designentscheidungen werden zu unterschiedlichen Ergebnissen führen!

## Vorgehen

In der Fallstudie werden diverse **Szenarien** aus dem Betrieb eines kleinen Programmkinos betrachtet, u.a. der
Kartenverkauf, dessen IST-Prozess in einer **Domain Story** mit [egon.io](https://egon.io) erfasst wurde:

![Kartenverkauf](docs/diagrams/Szenario-1-Kartenverkauf.egn.svg)

Aus diesen Szenarien wurden über das **Strategische Design** aus der Gesamtdomäne folgende **Subdomänen**
ermittelt:

- Wochenplanerstellung
- Filmauswahl
- Kartenverkauf
- Einlasskontrolle
- Filmvorführung
- Snackverkauf

Für die Implementierung dieser Beispielanwendung konzentrieren wir uns auf folgende **Bounded Contexts**:

- Filmauswahl
- Kartenverkauf

Diese Aufteilung ermöglicht uns pro Bounded Context ein spezifisches Fachmodell bzw. **Domänenmodel** zu entwickeln.
Die wichtigsten Begriffe der jeweiligen **Ubiquitous Language** sind in einem [Glossar](docs/Glossar.md) definiert.

## Features

Es wird zunächst die Filmauswahl, a.k.a das Kinoprogramm, angezeigt. Diese umfasst folgende Funktionen:

- Anzeige der aktuellen Woche als Kalenderleiste
- Anzeige der Filmvorstellungen des ausgewählten Wochentages

Nicht wundern: das "heutige" Datum ist im Code fest auf den 19.03.2025 gesetzt, da die Beispieldaten statisch hinterlegt
sind. Durch Klick auf eine Vorstellung gelangt man zum Kartenverkauf. Dieser umfasst folgende Funktionen:

- Anzeige der gewählten Vorstellung
- Angabe der gewünschten Anzahl von Kinokarten
- Auswahl eines Blocks zusammenhängender Plätze im Saalplan, mit initialem Vorschlag durch das System
- Zahlungsvorgang (angedeutet)
- Ausstellen der Kinokarten

## Bauen und Starten der Anwendung

Das gesamte System inklusive der Docker Images kann über Maven gebaut werden:

```bash
./mvnw clean package
```

Dieser Befehl baut das Spring Boot Backend, das Angular Frontend (mit dem frontend-maven-plugin) und erstellt Docker
Images für beide Komponenten (mit dem jib-maven-plugin und fabric8-docker-maven-plugin). Die Container können nun mit
Docker Compose gestartet werden:

```bash
docker compose up -d
```

Die Anwendung ist dann verfügbar unter: http://localhost:8081

Sollte die Anwendung über eine Entwicklungsumgebung gebaut und gestartet werden, ist die UI verfügbar
unter: http://localhost:4200

## Technologien

Das Backend basiert u.a. auf folgenden Technologien:

- Java
- Spring Boot
- JPA/Hibernate
- Lombok
- jMolecules

Das Frontend basiert u.a. auf folgenden Technologien:

- Angular
- TypeScript
- TailwindCSS
- DaisyUI

Die vollständige SBOM (Software Bill of Materials) wird mit maven package generiert und unter `backend/target/bom.xml`
bzw. `frontend/dist/frontend/.bom/bom.xml` abgelegt.

## Architektur

Die Anwendung ist als modularer Monolith (Modulith) strukturiert, dessen oberste Module auf den Bounded Contexts
basieren (fachliche Schnitte). Je Bounded Context kann ein eigener, auf die funktionalen und nicht-funktionalen
Anforderungen zurechtgeschnittener, Architekturstil gewählt werden, in diesem Fall eine Schichtenarchitektur und eine
hexagonale Architektur:

- Filmauswahl: eine simple **Schichtenarchitektur** unter direkter Verwendung der Spring Boot Boardmittel:
  RestController, Service, Repository, Entity (DTO, Domain-Entity, JPA-Entity in Einem).
- Kartenverkauf: eine **hexagonale Architektur** (Ports and Adapters) mit DDD-Bausteinen im fachlichen Kern und eigenen
  Modellen in den Adaptern (DTOs, JPA-Entities, und entsprechende Mapper).

Folgende Libraries und Tools helfen sicherzustellen, dass die Architekturregeln eingehalten werden:

- [jMolecules](https://github.com/xmolecules/jmolecules): stellt **Annotationen** wie `@PrimaryAdapter` oder
  `@AgregateRoot`, `@Entity`, und `@ValueObject` bereit, mit denen die entsprechenden Komponenten ausgezeichnet werden.
- [ArchUnit](https://www.archunit.org/): Prüft diverse **Architekturregeln** bzgl. der hexagonalen
  Architektur und der DDD-Mustersprache, z.B. dass ein `@AgregateRoot` zwar ein `@Entity` aber kein anderes
  `@AgregateRoot` enthalten darf.
- [Sonargraph](https://www.hello2morrow.com/products/sonargraph): Von uns regelmäßig in
  unseren [Architektur-Reviews](https://www.wps.de/leistungen/architektur-review) eingesetztes Tool zur explorativen
  Betrachtung der Architektur nach den drei Aspekten **technische Schichtung**, **fachliche Schichtung**,
  und **Mustersprache**.

## Lizenz

Dieses Projekt steht unter der [MIT Lizenz](LICENSE).

Copyright (c) 2025 WPS - Workplace Solutions GmbH

