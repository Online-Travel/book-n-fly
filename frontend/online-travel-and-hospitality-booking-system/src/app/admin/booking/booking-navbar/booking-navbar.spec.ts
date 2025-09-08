import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingNavbar } from './booking-navbar';

describe('BookingNavbar', () => {
  let component: BookingNavbar;
  let fixture: ComponentFixture<BookingNavbar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookingNavbar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookingNavbar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
