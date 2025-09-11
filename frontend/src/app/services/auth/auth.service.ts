import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

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
    return localStorage.removeItem('token');
  }


  isAuthenticated(): boolean {
    // Implement authentication check logic here
    return false;
  }
}
