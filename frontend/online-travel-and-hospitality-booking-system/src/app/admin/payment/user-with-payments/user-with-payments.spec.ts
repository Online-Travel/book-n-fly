import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserWithPayments } from './user-with-payments';

describe('UserWithPayments', () => {
  let component: UserWithPayments;
  let fixture: ComponentFixture<UserWithPayments>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserWithPayments]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserWithPayments);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
