import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MeComponent } from './me.component';
import { UserService } from 'src/app/services/user.service';
import { of } from 'rxjs';


describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    },
    logOut: jest.fn() // on mock la méthode logOut
  };

  // on mock la méthode navigate
  const mockRouter = {
    navigate: jest.fn()
  };

  // on mock la méthode open
  const mockMatSnackBar = {
    open: jest.fn()
  };

  // on mock la méthode back
  const mockWindow = {
    history: {
      back: jest.fn()
    }
  };

  // on mock les méthodes getById et delete
  const mockUserService = {
    getById: jest.fn().mockReturnValue(of({ id: 1 })),
    delete: jest.fn().mockReturnValue(of({}))
};

  beforeEach(async () => {

    // On remplace la méthode back par celle de mockWindow
    jest.spyOn(window.history, 'back').mockImplementation(mockWindow.history.back);

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: UserService, useValue: mockUserService },
        { provide: Router, useValue: mockRouter },
        { provide: MatSnackBar, useValue: mockMatSnackBar }
      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test : Verifié que la session est bien détruite
  it('should correctly delete the session', () => {
    component.delete();

    expect(mockUserService.delete).toHaveBeenCalledWith('1');
    expect(mockUserService.delete).toHaveBeenCalledTimes(1);
    expect(mockMatSnackBar.open).toHaveBeenCalledWith("Your account has been deleted !", 'Close', { duration: 3000 });
    expect(mockSessionService.logOut).toHaveBeenCalled();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/']);
  });

  // Test : On verifie que la méthode back() est bien appelée
  it('should call the back method', () => {
    component.back();

    expect(mockWindow.history.back).toHaveBeenCalled();
  });
});
