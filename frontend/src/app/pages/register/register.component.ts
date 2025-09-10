import { Component } from '@angular/core';

@Component({
  selector: 'app-regis',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
username: any;
email: any;
password: any;
confirmPassword: any;
showPassword = false;

constructor() { }

onSubmit() {
throw new Error('Method not implemented.');
}

}
