import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateEpicComponent } from './update-epic.component';

describe('UpdateEpicComponent', () => {
  let component: UpdateEpicComponent;
  let fixture: ComponentFixture<UpdateEpicComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateEpicComponent]
    });
    fixture = TestBed.createComponent(UpdateEpicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
