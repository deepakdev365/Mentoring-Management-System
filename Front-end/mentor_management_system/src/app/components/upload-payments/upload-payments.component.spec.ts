import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadPaymentsComponent } from './upload-payments.component';

describe('UploadPaymentsComponent', () => {
  let component: UploadPaymentsComponent;
  let fixture: ComponentFixture<UploadPaymentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UploadPaymentsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadPaymentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
