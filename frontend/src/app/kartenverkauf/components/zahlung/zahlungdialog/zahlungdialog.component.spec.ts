import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZahlungdialogComponent } from './zahlungdialog.component';

describe('ZahlungdialogComponent', () => {
  let component: ZahlungdialogComponent;
  let fixture: ComponentFixture<ZahlungdialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ZahlungdialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ZahlungdialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
