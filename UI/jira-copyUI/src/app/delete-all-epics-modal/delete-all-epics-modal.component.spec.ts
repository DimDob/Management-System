import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteAllEpicsModalComponent } from './delete-all-epics-modal.component';

describe('DeleteAllEpicsModalComponent', () => {
  let component: DeleteAllEpicsModalComponent;
  let fixture: ComponentFixture<DeleteAllEpicsModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteAllEpicsModalComponent]
    });
    fixture = TestBed.createComponent(DeleteAllEpicsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
