import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourceServerComponent } from './resource-server.component';

describe('ResourceServerComponent', () => {
  let component: ResourceServerComponent;
  let fixture: ComponentFixture<ResourceServerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResourceServerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceServerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
