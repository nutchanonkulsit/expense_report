import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from '../../environments/environment';
import { catchError, lastValueFrom, map, Observable, of } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  login(email: string, password: string) {
    return this.http.post(`${environment.apiUrl}/auth/login`, {
      email,
      password,
    });
  }

  register(name: string, email: string, password: string, role: string) {
    return this.http.post(`${environment.apiUrl}/auth/register`, {
      name,
      email,
      password,
      role,
    });
  }

  logout(): void {
    // Only access localStorage if the application is running in a browser
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('token');
      this.router.navigate(['/login']);
    }
  }

  // isTokenValid(token: string): Observable<boolean> {
  //   if (!token) return of(false);

  //   const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

  //   return this.http
  //     .post(
  //       `${environment.apiUrl}/auth/validate-token`,
  //       {},
  //       { headers, responseType: 'text' }
  //     )
  //     .pipe(
  //       map((res) => {
  //         return res === 'Token is valid';
  //       }),
  //       catchError((err) => {
  //         console.error('Token validation error:', err);
  //         return of(false); // any error → token invalid
  //       })
  //     );
  // }

  // Validate token
  // isTokenValid(token: string): Promise<boolean> {
  //   if (!token) return Promise.resolve(false);

  //   const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

  //   return lastValueFrom(
  //     this.http
  //       .post(
  //         `${environment.apiUrl}/auth/validate-token`,
  //         {},
  //         { headers, responseType: 'text' }
  //       )
  //       .pipe(
  //         map((res) => res === 'Token is valid'),
  //         catchError((err) => {
  //           console.error('Token validation error:', err);
  //           return of(false);
  //         })
  //       )
  //   );
  // }

  isTokenValid(token: string): Observable<boolean> {
    if (!token) return of(false);

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http
      .post(`${environment.apiUrl}/auth/validate-token`, {}, { headers })
      .pipe(
        map(() => true),
        catchError((err) => {
          console.error('Token validation error:', err);
          return of(false);
        })
      );
  }

  isAuthenticated(): boolean {
    // Implement authentication check logic here
    return false;
  }
}
