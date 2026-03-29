import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeAnnouncementComponent } from './make-announcement.component';

describe('MakeAnnouncementComponent', () => {
  let component: MakeAnnouncementComponent;
  let fixture: ComponentFixture<MakeAnnouncementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MakeAnnouncementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MakeAnnouncementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
