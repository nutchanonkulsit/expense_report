import { NgModule } from '@angular/core';
import {
  BrowserModule,
  provideClientHydration,
  withEventReplay,
} from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule,provideHttpClient, withFetch } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { RegisterComponent } from './pages/register/register.component';
import { RegisterFormComponent } from './components/register-form/register-form.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { NavBarComponent } from './layouts/nav-bar/nav-bar.component';
import { NewExpenseComponent } from './pages/new-expense/new-expense.component';
import { UserInfoComponent } from './pages/user-info/user-info.component';
import { DashboardCardComponent } from './components/dashboard-card/dashboard-card.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { DialogExpenseComponent } from './components/dialog-expense/dialog-expense.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    LoginFormComponent,
    RegisterComponent,
    RegisterFormComponent,
    DashboardComponent,
    NavBarComponent,
    NewExpenseComponent,
    UserInfoComponent,
    DashboardCardComponent,
    DialogExpenseComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    MatDialogModule,
  ],
  providers: [
    provideClientHydration(withEventReplay()),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    provideHttpClient(), 
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
