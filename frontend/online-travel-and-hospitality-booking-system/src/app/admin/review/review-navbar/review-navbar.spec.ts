import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewNavbar } from './review-navbar';

describe('ReviewNavbar', () => {
  let component: ReviewNavbar;
  let fixture: ComponentFixture<ReviewNavbar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReviewNavbar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewNavbar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
