import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { NewExpenseComponent } from './pages/new-expense/new-expense.component';
import { NavBarComponent } from './layouts/nav-bar/nav-bar.component';
import { LoginBlockGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [LoginBlockGuard] },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [LoginBlockGuard],
  },

  {
    path: '',
    component: NavBarComponent,
    children: [
      { path: '', component: DashboardComponent },
      { path: 'new-expense', component: NewExpenseComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
