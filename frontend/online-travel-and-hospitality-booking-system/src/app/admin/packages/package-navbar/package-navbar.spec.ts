import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PackageNavbar } from './package-navbar';

describe('PackageNavbar', () => {
  let component: PackageNavbar;
  let fixture: ComponentFixture<PackageNavbar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PackageNavbar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PackageNavbar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
