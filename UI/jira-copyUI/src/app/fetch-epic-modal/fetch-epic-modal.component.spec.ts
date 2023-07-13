import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FetchEpicModalComponent } from './fetch-epic-modal.component';

describe('FetchEpicModalComponent', () => {
  let component: FetchEpicModalComponent;
  let fixture: ComponentFixture<FetchEpicModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FetchEpicModalComponent]
    });
    fixture = TestBed.createComponent(FetchEpicModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
