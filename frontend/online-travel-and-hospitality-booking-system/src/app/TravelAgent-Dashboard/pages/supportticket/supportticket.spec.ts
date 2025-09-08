import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Supportticket } from './supportticket';

describe('Supportticket', () => {
  let component: Supportticket;
  let fixture: ComponentFixture<Supportticket>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Supportticket]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Supportticket);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
