import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class LoginBlockGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(): boolean {
    const token = localStorage.getItem('token');
    if (token) {
      this.authService.isTokenValid().then((isValid) => {
        if (isValid) {
          this.router.navigate(['/']);
          return false;
        }
        return true;
      });
    }
    return true;
  }
}
