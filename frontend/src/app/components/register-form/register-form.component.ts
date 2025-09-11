import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-register-form',
  standalone: false,
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css',
})
export class RegisterFormComponent {
  password: string = '';
  confirmPassword: string = '';
  email: string = '';
  name: string = '';
  isLoading: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  get passwordsMatch(): boolean {
    return this.password === this.confirmPassword;
  }

  onSubmit() {
    this.isLoading = true;

    this.authService
      .register(this.name, this.email, this.password, 'user')
      .subscribe({
        next: (response) => {
          console.log('Registration successful', response);
          this.authService.login(this.email, this.password).subscribe({
            next: (res: any) => {
              console.log('Login successful', res);
              if (res && res.token) {
                localStorage.setItem('token', res.token);
              }
              this.isLoading = false;
              // Redirect to dashboard or another page
              this.router.navigate(['/dashboard']);
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
