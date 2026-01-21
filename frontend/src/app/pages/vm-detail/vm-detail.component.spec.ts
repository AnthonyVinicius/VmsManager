import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VmDetailComponent } from './vm-detail.component';

describe('VmDetailComponent', () => {
  let component: VmDetailComponent;
  let fixture: ComponentFixture<VmDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VmDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VmDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
