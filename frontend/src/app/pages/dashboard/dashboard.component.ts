import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { platformBrowser } from '@angular/platform-browser';
import { isPlatformBrowser } from '@angular/common';
import { DialogExpenseComponent } from '../../components/dialog-expense/dialog-expense.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  openAddExpenseDialog() {
    this.dialog.open(DialogExpenseComponent, {
      data: {
        isEdit: false,
        title: 'Test Prop',
      },
    });
  }
  constructor(private userService: UserService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe((users) => {
      console.log(users);
    });
  }
}
