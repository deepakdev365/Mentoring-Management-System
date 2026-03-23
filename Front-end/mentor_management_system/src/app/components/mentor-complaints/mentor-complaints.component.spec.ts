import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MentorComplaintsComponent } from './mentor-complaints.component';

describe('MentorComplaintsComponent', () => {
  let component: MentorComplaintsComponent;
  let fixture: ComponentFixture<MentorComplaintsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MentorComplaintsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MentorComplaintsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
