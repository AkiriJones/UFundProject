import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerCupboardComponent } from './manager-cupboard.component';

describe('ManagerCupboardComponent', () => {
  let component: ManagerCupboardComponent;
  let fixture: ComponentFixture<ManagerCupboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ManagerCupboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagerCupboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
