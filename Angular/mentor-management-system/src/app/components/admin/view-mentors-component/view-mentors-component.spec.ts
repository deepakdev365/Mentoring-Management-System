import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMentorsComponent } from './view-mentors-component';

describe('ViewMentorsComponent', () => {
  let component: ViewMentorsComponent;
  let fixture: ComponentFixture<ViewMentorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewMentorsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewMentorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
