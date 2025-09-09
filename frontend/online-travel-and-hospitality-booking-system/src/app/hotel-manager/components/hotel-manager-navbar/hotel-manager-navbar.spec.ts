import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelManagerNavbar } from './hotel-manager-navbar';

describe('HotelManagerNavbar', () => {
  let component: HotelManagerNavbar;
  let fixture: ComponentFixture<HotelManagerNavbar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HotelManagerNavbar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HotelManagerNavbar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
