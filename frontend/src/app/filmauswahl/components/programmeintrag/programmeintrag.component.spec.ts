import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ProgrammeintragComponent} from './programmeintrag.component';
import {provideHttpClient} from '@angular/common/http';
import {provideHttpClientTesting} from '@angular/common/http/testing';

describe('ProgrammeintragComponent', () => {
  let component: ProgrammeintragComponent;
  let fixture: ComponentFixture<ProgrammeintragComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        ProgrammeintragComponent
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ProgrammeintragComponent);
    component = fixture.componentInstance;
    component.film = {
      id: 1,
      titel: 'Inception',
      laufzeit: 148,
      posterUrl: 'https://example.com/posters/inception.jpg',
      fsk: 12,
      beschreibung:
        'Ein Dieb, der Geheimnisse aus den Träumen anderer stiehlt, erhält die Chance, seine Vergangenheit auszulöschen.',
      genre: 'Science-Fiction',
      hauptdarsteller: 'Leonardo DiCaprio',
      regie: 'Christopher Nolan',
      sprache: 'Deutsch',
      vorstellungen: [
        {
          uuid: '123e4567-e89b-12d3-a456-426614174000',
          beginn: '2025-03-16T20:00:00',
          preis: 12.5,
          saal: 'Saal 1',
        },
        {
          uuid: '123e4567-e89b-12d3-a456-426614174001',
          beginn: '2025-03-17T18:30:00',
          preis: 11.0,
          saal: 'Saal 2',
        },
      ],
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
