import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MentorStudentBacklogs } from './mentor-student-backlogs';

describe('MentorStudentBacklogs', () => {
  let component: MentorStudentBacklogs;
  let fixture: ComponentFixture<MentorStudentBacklogs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MentorStudentBacklogs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MentorStudentBacklogs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
