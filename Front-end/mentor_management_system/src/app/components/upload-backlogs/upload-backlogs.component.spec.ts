import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadBacklogsComponent } from './upload-backlogs.component';

describe('UploadBacklogsComponent', () => {
  let component: UploadBacklogsComponent;
  let fixture: ComponentFixture<UploadBacklogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UploadBacklogsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadBacklogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
