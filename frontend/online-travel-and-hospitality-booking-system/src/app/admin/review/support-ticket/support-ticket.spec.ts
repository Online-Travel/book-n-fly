import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupportTicket } from './support-ticket';

describe('SupportTicket', () => {
  let component: SupportTicket;
  let fixture: ComponentFixture<SupportTicket>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SupportTicket]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SupportTicket);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
