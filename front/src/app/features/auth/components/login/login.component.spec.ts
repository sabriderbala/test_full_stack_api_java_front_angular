import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from '../../services/auth.service';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  const mockAuthService = {
    login: jest.fn()
  };

  const mockRouter = {
    navigate: jest.fn()
  };



  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter }, // <-- Mock du Router
        SessionService
      ],      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Test : On vérifie que le composant est bien créé
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('LoginComponent#Submit', () => {
    // test : On vérifie que le login marche
    it('should login', () => {
      mockAuthService.login.mockReturnValue(of({ token: 'fake_token' }));

      component.form.controls['email'].setValue('jean@test.com');
      component.form.controls['password'].setValue('jean123');
      component.submit();

      expect(component).toBeTruthy();
    });

    // test : On vérifie que le login ne marche pas
    it('should not login', () => {
      mockAuthService.login.mockReturnValue(throwError('Error'));

      component.form.controls['email'].setValue('');
      component.form.controls['password'].setValue('');
      component.submit();

      expect(component.onError).toBe(true);
    });
  });
});
