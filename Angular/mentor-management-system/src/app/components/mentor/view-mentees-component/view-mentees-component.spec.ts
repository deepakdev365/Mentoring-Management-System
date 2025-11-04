import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMenteesComponent } from './view-mentees-component';

describe('ViewMenteesComponent', () => {
  let component: ViewMenteesComponent;
  let fixture: ComponentFixture<ViewMenteesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewMenteesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewMenteesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
