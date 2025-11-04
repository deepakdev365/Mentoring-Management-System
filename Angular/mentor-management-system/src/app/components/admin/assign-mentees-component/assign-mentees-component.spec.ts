import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignMenteesComponent } from './assign-mentees-component';

describe('AssignMenteesComponent', () => {
  let component: AssignMenteesComponent;
  let fixture: ComponentFixture<AssignMenteesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssignMenteesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssignMenteesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
