import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlatzanzahlComponent } from './platzanzahl.component';

describe('PlatzanzahlComponent', () => {
  let component: PlatzanzahlComponent;
  let fixture: ComponentFixture<PlatzanzahlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlatzanzahlComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlatzanzahlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
