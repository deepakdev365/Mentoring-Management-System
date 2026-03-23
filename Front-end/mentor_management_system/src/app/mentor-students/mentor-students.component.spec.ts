import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MentorStudentsComponent } from './mentor-students.component';

describe('MentorStudentsComponent', () => {
  let component: MentorStudentsComponent;
  let fixture: ComponentFixture<MentorStudentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MentorStudentsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MentorStudentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
