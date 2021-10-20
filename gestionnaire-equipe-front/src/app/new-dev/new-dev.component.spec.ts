import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewDevComponent } from './new-dev.component';

describe('NewDevComponent', () => {
  let component: NewDevComponent;
  let fixture: ComponentFixture<NewDevComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewDevComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewDevComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
