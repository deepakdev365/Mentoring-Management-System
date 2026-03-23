import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarksUploadComponent } from './marks-upload.component';

describe('MarksUploadComponent', () => {
  let component: MarksUploadComponent;
  let fixture: ComponentFixture<MarksUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MarksUploadComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MarksUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
