import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentNavbar } from './payment-navbar';

describe('PaymentNavbar', () => {
  let component: PaymentNavbar;
  let fixture: ComponentFixture<PaymentNavbar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaymentNavbar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaymentNavbar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
