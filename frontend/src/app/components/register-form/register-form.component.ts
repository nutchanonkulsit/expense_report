import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';
import { User } from '../../models/user.model';
@Component({
  selector: 'app-register-form',
  standalone: false,
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css',
})
export class RegisterFormComponent {

  user: User = {
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: 'user',
  };

  isLoading: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  get passwordsMatch(): boolean {
    return this.user.password === this.user.confirmPassword;
  }

  onSubmit() {
    this.isLoading = true;

    this.authService
      .register(
        this.user.name ?? '',
        this.user.email ?? '',
        this.user.password ?? '',
        'user'
      )
      .subscribe({
        next: (response) => {
          console.log('Registration successful', response);
          this.authService
            .login(this.user.email ?? '', this.user.password ?? '')
            .subscribe({
              next: (res: any) => {
                console.log('Login successful', res);
                if (res && res.token) {
                  localStorage.setItem('token', res.token);
                }
                this.isLoading = false;
                // Redirect to dashboard or another page
                this.router.navigate(['/']);
              },
              error: (loginError) => {
                console.error('Login failed', loginError);
                this.isLoading = false;
              },
            });
        },
        error: (error) => {
          console.error('Registration failed', error);
        },
      });
  }
}
