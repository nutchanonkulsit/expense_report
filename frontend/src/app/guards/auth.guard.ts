import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { AuthService } from '../services/auth/auth.service';
import { from, Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class LoginBlockGuard implements CanActivate {
  constructor(
    private router: Router,
    private authService: AuthService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  canActivate(): Observable<boolean> {
    if (!isPlatformBrowser(this.platformId)) {
      return of(true);
    }

    const token = localStorage.getItem('token');

    if (!token) return of(true);

    return from(this.authService.isTokenValid(token)).pipe(
      map((isValid) => {
        if (isValid) {
          this.router.navigate(['/']);
          return false;
        }
        return true;
      }),
      catchError((err) => {
        console.error('Guard error:', err);
        return of(true);
      })
    );
  }
}
