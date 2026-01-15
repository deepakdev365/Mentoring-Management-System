import { Routes } from '@angular/router';
import { LandingComponent } from './components/landing.component/landing.component';
import { AdminLoginComponent } from './components/admin-login.component/admin-login.component';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'admin-login', component: AdminLoginComponent }
];
