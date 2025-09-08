import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TravellerDashboardComponent } from './traveller-dashboard.component';

describe('TravellerDashboardComponent', () => {
  let component: TravellerDashboardComponent;
  let fixture: ComponentFixture<TravellerDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TravellerDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TravellerDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
