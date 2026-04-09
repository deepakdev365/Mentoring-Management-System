import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MentorLowAttendance } from './mentor-low-attendance';

describe('MentorLowAttendance', () => {
  let component: MentorLowAttendance;
  let fixture: ComponentFixture<MentorLowAttendance>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MentorLowAttendance]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MentorLowAttendance);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
