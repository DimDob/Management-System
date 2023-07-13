import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteEpicModalComponent } from './delete-epic-modal.component';

describe('DeleteEpicModalComponent', () => {
  let component: DeleteEpicModalComponent;
  let fixture: ComponentFixture<DeleteEpicModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteEpicModalComponent]
    });
    fixture = TestBed.createComponent(DeleteEpicModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
