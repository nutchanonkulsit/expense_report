import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  standalone: false,
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css',
})
export class LoginFormComponent {
  showPassword = false;

  constructor(private authservice: AuthService, private route: Router) {}

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  isLoading: any;
  onSubmit() {
    this.isLoading = true;

    this.authservice.login(this.username, this.password).subscribe({
      next: (res: any) => {
        if (res && res.token) {
          alert('Login Successful ✅');
          localStorage.setItem('token', res.token);
          console.log('res:', res);

          // if backend returns user info
          if (res.user) {
            localStorage.setItem('user', JSON.stringify(res.user));
          }

          this.route.navigate(['/']);
        } else {
          alert('Login Failed ❌ - Invalid response');
        }
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Login Error:', err);
        alert('Login Failed ❌ - Wrong username or password');
      },
    });
  }

  username: any;
  password: any;
}
