import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-expense',
  standalone: false,
  templateUrl: './dialog-expense.component.html',
  styleUrl: './dialog-expense.component.css',
})
export class DialogExpenseComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
}
