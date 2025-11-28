INSERT INTO filmauswahl.filme
(titel, laufzeit, poster_url, fsk, beschreibung, genre, hauptdarsteller, regie, sprache)
VALUES ('Star Boars', 125, 'assets/Star_Boars.jpeg', 12, 'In einer weit, weit entfernten Galaxie kämpfen mutige Wildschweine gegen das tyrannische Imperium.
Angeführt von Luke Stywalker, müssen sie sich mit Lichttrüffeln und telepathischen Grunzkraftfähigkeiten gegen den dunklen Lord Swineous behaupten.
Ein episches Sci-Fi-Abenteuer voller Action, Humor und intergalaktischem Speckduft!',
        'Sci-Fi, Comedy', 'Luke Stywalker', 'George Laxus', 'deutsch'),
       ('Guardians of the Lunacy', 95, 'assets/Guardians_Of_The_Lunacy.jpeg', 6,
        'Eine Truppe aus abgedrehten Außenseitern wird widerwillig zum Schutz des Universums verpflichtet – und das ist kein gutes Zeichen.
Chris Plattfall und sein chaotisches Team aus galaktischen Verrückten stürzen sich in explosive Abenteuer voller skurriler Aliens,
dummer Sprüche und unerwarteter Heldentaten. Werden sie das Universum retten? Wahrscheinlich nicht. Aber es wird lustig!',
        'Sci-Fi, Comedy', 'Chris Plattfall',
        'James Gunner', 'englisch'),
       ('Back to the Futura', 116, 'assets/Back_To_The_Futura.jpeg', 12,
        'Als die junge Marty McGigawatts mit einer experimentellen Zeitmaschine in die Zukunft reist, findet sie sich in einer dystopischen Megacity wieder,
in der Roboter die Welt regieren. Mit der Hilfe eines exzentrischen Erfinders und einem Hoverboard muss sie den Lauf der Geschichte ändern,
bevor sie in einer Endlosschleife der Zeit gefangen bleibt.', 'Sci-Fi, Adventure', 'Marty McGigawatts',
        'Robert Zoomekis', 'deutsch'),
       ('Clown Wars', 105, 'assets/Clown_Wars.jpeg', 18,
        'Die Erde wird von einer Horde außerirdischer Clowns angegriffen, die nichts anderes wollen, als die Menschheit mit tödlichen Gags zu unterwerfen.
Nur eine Gruppe rebellischer Spaßmacher kann sich der Bedrohung entgegenstellen. Ein intergalaktisches Spektakel voller Ballontier-Kriege,
Killer-Jojos und einem epischen Showdown in der Zirkusarena des Todes.', 'Sci-Fi, Horror, Comedy', 'Penny Wisecrack',
        'Tim Burtonisch', 'englisch'),
       ('The Fast and the Curious', 135, 'assets/The_Fast_And_The_Curious.jpeg', 16,
        'The Fast and the Curious ist ein actiongeladener Film über eine Bande von hochintelligenten Straßenkatzen, die illegale Straßenrennen fahren und geheime Raubüberfälle planen.
Angeführt von der waghalsigen und charismatischen Kätzin Velo, entdeckt das Team, dass eine rivalisierende Hunde-Gang versucht, die Straßen zu übernehmen.
Während atemberaubender Verfolgungsjagden, waghalsiger Stunts und cleverer Pläne müssen die Katzen nicht nur ihre Revierhoheit verteidigen, sondern auch ein letztes, spektakuläres Rennen gewinnen, um ihre Freiheit zu sichern.
Ein rasanter Mix aus Action, Humor und katzenhafter Cleverness!',
        'Action, Adventure, Tierfilm', 'Cat Moss',
        'Rob Kitten', 'deutsch');


INSERT INTO filmauswahl.saele
    (name)
VALUES ('großer Saal'),
       ('kleiner Saal');


INSERT INTO filmauswahl.vorstellungen
    (uuid, beginn, film_id, saal_id)
VALUES ('70f79a3c-eb2f-48e4-af59-cda7a353635f', '2025-03-17 14:30:00', 1, 1),
       ('f142de00-f3ec-4a42-9493-d406b3062b4a', '2025-03-18 15:30:00', 1,  2),
       ('57d71cca-91c9-4876-a1b3-5628da00abd3', '2025-03-19 14:30:00', 2, 1),
       ('f00cd3c6-b059-4138-9f10-4ba2813fa162', '2025-03-19 15:00:00', 5, 2),
       ('0299be34-d6ea-4eba-b4e8-cdd41afc7da8', '2025-03-19 20:30:00', 5, 1),
       ('503f9e1f-4575-4cb5-8162-748bb8b8c26f', '2025-03-19 22:30:00', 4,  2),
       ('80b9d36c-374f-4569-9ff1-8a2ed2f02b3d', '2025-03-20 14:30:00', 2, 2),
       ('b167534a-8d9a-4219-ba92-69d9a2c1212b', '2025-03-20 15:30:00', 3, 1),
       ('bb98a2af-3d0a-4cb8-9418-61d58ecccf02', '2025-03-20 20:00:00', 2, 1),
       ('66640528-1d70-4564-bae9-a72de8a9a4de', '2025-03-21 15:00:00', 5, 1),
       ('fc5e4025-aee4-42fe-8e81-17628e9b478e', '2025-03-21 16:15:00', 3, 2),
       ('e7c0edd7-0904-470c-954e-cb9e0030ca12', '2025-03-21 20:30:00', 3, 1),
       ('15c935ac-dddc-4fc3-b47f-baa5a642075c', '2025-03-21 22:30:00', 4,  2),
       ('75e542da-92c0-4559-bfbf-e707af724f7d', '2025-03-22 14:30:00', 3, 1),
       ('6e568065-1850-40fb-8930-2bd93f5ac242', '2025-03-22 15:30:00', 1,  2),
       ('791df6e3-15c6-410d-9a77-548f4eb9db48', '2025-03-22 19:30:00', 2, 2),
       ('44884037-b0c8-422d-90b6-da107659981e', '2025-03-22 20:45:00', 1,  1),
       ('090c173a-3636-4980-865a-1ec859eb4f90', '2025-03-23 14:30:00', 5, 2),
       ('f711a38d-e792-4016-9463-286c96ce824e', '2025-03-23 15:45:00', 2, 1),
       ('c728df78-e6a6-4715-be16-0fb56698af08', '2025-03-23 19:30:00', 5, 1),
       ('20ad7f5f-7167-46b9-ada6-e7e7c4bd65aa', '2025-03-23 22:30:00', 4,  2);


INSERT INTO kartenverkauf.vorstellungen
    (uuid, beginn, saal, film, kategorie)
VALUES ('70f79a3c-eb2f-48e4-af59-cda7a353635f', '2025-03-17 14:30:00', 'großer Saal', 'Star Boars', 'Standard'),
       ('f142de00-f3ec-4a42-9493-d406b3062b4a', '2025-03-18 15:30:00', 'kleiner Saal', 'Star Boars', 'Standard'),
       ('57d71cca-91c9-4876-a1b3-5628da00abd3', '2025-03-19 14:30:00', 'großer Saal', 'Guardians of the Lunacy', 'Kinderfilm'),
       ('f00cd3c6-b059-4138-9f10-4ba2813fa162', '2025-03-19 15:00:00', 'kleiner Saal', 'The Fast and the Curious', 'Standard'),
       ('0299be34-d6ea-4eba-b4e8-cdd41afc7da8', '2025-03-19 20:30:00', 'großer Saal', 'The Fast and the Curious', 'PrimeTime'),
       ('503f9e1f-4575-4cb5-8162-748bb8b8c26f', '2025-03-19 22:30:00', 'kleiner Saal', 'Clown Wars', 'Standard'),
       ('80b9d36c-374f-4569-9ff1-8a2ed2f02b3d', '2025-03-20 14:30:00', 'kleiner Saal', 'Guardians of the Lunacy', 'Kinderfilm'),
       ('b167534a-8d9a-4219-ba92-69d9a2c1212b', '2025-03-20 15:30:00', 'großer Saal', 'Back to the Futura', 'Standard'),
       ('bb98a2af-3d0a-4cb8-9418-61d58ecccf02', '2025-03-20 20:00:00', 'großer Saal', 'Guardians of the Lunacy', 'PrimeTime'),
       ('66640528-1d70-4564-bae9-a72de8a9a4de', '2025-03-21 15:00:00', 'großer Saal', 'The Fast and the Curious', 'Standard'),
       ('fc5e4025-aee4-42fe-8e81-17628e9b478e', '2025-03-21 16:15:00', 'kleiner Saal', 'Back to the Futura', 'Standard'),
       ('e7c0edd7-0904-470c-954e-cb9e0030ca12', '2025-03-21 20:30:00', 'großer Saal', 'Back to the Futura', 'PrimeTime'),
       ('15c935ac-dddc-4fc3-b47f-baa5a642075c', '2025-03-21 22:30:00', 'kleiner Saal', 'Clown Wars','Standard'),
       ('75e542da-92c0-4559-bfbf-e707af724f7d', '2025-03-22 14:30:00', 'großer Saal', 'Back to the Futura', 'Standard'),
       ('6e568065-1850-40fb-8930-2bd93f5ac242', '2025-03-22 15:30:00', 'kleiner Saal', 'Star Boars', 'Standard'),
       ('791df6e3-15c6-410d-9a77-548f4eb9db48', '2025-03-22 19:30:00', 'kleiner Saal', 'Guardians of the Lunacy', 'Kinderfilm'),
       ('44884037-b0c8-422d-90b6-da107659981e', '2025-03-22 20:45:00', 'großer Saal', 'Star Boars', 'PrimeTime'),
       ('090c173a-3636-4980-865a-1ec859eb4f90', '2025-03-23 14:30:00', 'kleiner Saal', 'The Fast and the Curious', 'Standard'),
       ('f711a38d-e792-4016-9463-286c96ce824e', '2025-03-23 15:45:00', 'großer Saal', 'Guardians of the Lunacy', 'Kinderfilm'),
       ('c728df78-e6a6-4715-be16-0fb56698af08', '2025-03-23 19:30:00', 'großer Saal', 'The Fast and the Curious', 'PrimeTime'),
       ('20ad7f5f-7167-46b9-ada6-e7e7c4bd65aa', '2025-03-23 22:30:00', 'kleiner Saal', 'Clown Wars', 'Standard');
