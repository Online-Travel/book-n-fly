import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingWithPayments } from './booking-with-payments';

describe('BookingWithPayments', () => {
  let component: BookingWithPayments;
  let fixture: ComponentFixture<BookingWithPayments>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookingWithPayments]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookingWithPayments);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
