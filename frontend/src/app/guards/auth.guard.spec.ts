import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { LoginBlockGuard } from './auth.guard';

describe('LoginBlockGuard', () => {
  let guard: LoginBlockGuard;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      providers: [
        LoginBlockGuard,
        { provide: Router, useValue: routerSpy }
      ]
    });

    guard = TestBed.inject(LoginBlockGuard);
  });

  afterEach(() => {
    localStorage.clear();
  });

  it('should allow navigation if no token', () => {
    localStorage.removeItem('token');
    const result = guard.canActivate();
    expect(result).toBeTrue();
    expect(routerSpy.navigate).not.toHaveBeenCalled();
  });

  it('should block navigation and redirect if token exists', () => {
    localStorage.setItem('token', 'fake-token');
    const result = guard.canActivate();
    expect(result).toBeFalse();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
  });
});
