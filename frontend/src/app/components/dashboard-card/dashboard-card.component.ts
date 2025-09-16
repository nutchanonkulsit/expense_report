import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-dashboard-card',
  standalone: false,
  templateUrl: './dashboard-card.component.html',
  styleUrl: './dashboard-card.component.css'
})
export class DashboardCardComponent {
  @Input() title: string = '';
  @Input() amount: number = 0;
  @Input() icon: string = '';
}
