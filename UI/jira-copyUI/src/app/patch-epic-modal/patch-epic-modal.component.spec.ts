import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatchEpicModalComponent } from './patch-epic-modal.component';

describe('PatchEpicModalComponent', () => {
  let component: PatchEpicModalComponent;
  let fixture: ComponentFixture<PatchEpicModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PatchEpicModalComponent]
    });
    fixture = TestBed.createComponent(PatchEpicModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
