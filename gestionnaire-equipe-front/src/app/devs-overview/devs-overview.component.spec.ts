import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DevsOverviewComponent } from './devs-overview.component';

describe('DevsOverviewComponent', () => {
  let component: DevsOverviewComponent;
  let fixture: ComponentFixture<DevsOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DevsOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DevsOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
