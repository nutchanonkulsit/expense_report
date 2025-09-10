import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
    // Implement login logic here
    return this.http.post(`${environment.apiUrl}/auth/login`, {
      email,
      password,
    });
  }

  logout(): void {
    // Implement logout logic here
  }

  isAuthenticated(): boolean {
    // Implement authentication check logic here
    return false;
  }
}
