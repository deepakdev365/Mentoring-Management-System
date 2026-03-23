import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeComplaintComponent } from './make-complaint.component';

describe('MakeComplaintComponent', () => {
  let component: MakeComplaintComponent;
  let fixture: ComponentFixture<MakeComplaintComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MakeComplaintComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MakeComplaintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
