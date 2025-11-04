import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// 🔹 Import your components
import { LandingPageComponent } from './landing-page.component/landing-page.component';

import { StudentLoginComponent } from './components/login/student-login-component/student-login-component';
import { MentorLoginComponent } from './components/login/mentor-login-component/mentor-login-component'; 
import { AdminLoginComponent } from './components/login/admin-login-component/admin-login-component'; 
import { AdminDashboardComponent } from './components/admin/admin-dashboard-component/admin-dashboard-component';
import { AssignMenteesComponent } from './components/admin/assign-mentees-component/assign-mentees-component';
import { ViewMentorsComponent } from './components/admin/view-mentors-component/view-mentors-component';

import { AddStudentComponent } from './components/admin/add-student-component/add-student-component'; 
import { AddMentorComponent } from './components/admin/add-mentor-component/add-mentor-component'; 
import { MentorDashboardComponent } from './components/mentor/mentor-dashboard-component/mentor-dashboard-component';
import { StudentDashboardComponent } from './components/student/student-dashboard-component/student-dashboard-component';
import { ViewMenteesComponent } from './components/mentor/view-mentees-component/view-mentees-component'; 
import { ViewMentorComponent } from './components/student/view-mentor-component/view-mentor-component';
import { WriteComplaintComponent } from './components/student/write-complaint-component/write-complaint-component';

export const routes: Routes = [
//    { path: '', redirectTo: '/student/login', pathMatch: 'full' },
//   { path: 'student/login', component: StudentLoginComponent },
//   { path: 'mentor/login', component: MentorLoginComponent },
//   { path: 'admin/login', component: AdminLoginComponent },
//   { path: 'admin/add-student', component: AddStudentComponent },
//   { path: 'admin/add-mentor', component: AddMentorComponent },


//   { path: 'student/dashboard', component: StudentDashboardComponent },
//   { path: 'mentor/dashboard', component: MentorDashboardComponent },
//   { path: 'mentor/view-mentees', component: ViewMenteesComponent },
//   { path: 'student/view-mentor', component: ViewMentorComponent },
//    { path: '**', redirectTo: '/student/login' } // this will fall back to your AppComponent (landing page)
{ path: '', component: LandingPageComponent },
{ path: '', redirectTo: '/student/login', pathMatch: 'full' },
  { path: 'student/login', component: StudentLoginComponent },
  { path: 'mentor/login', component: MentorLoginComponent },
  { path: 'admin/login', component: AdminLoginComponent },
  { path: 'admin/dashboard', component: AdminDashboardComponent },
  { path: 'mentor/dashboard', component: MentorDashboardComponent },

  { path: 'student/dashboard', component: StudentDashboardComponent },
  { path: 'admin/add-student', component: AddStudentComponent },
  { path: 'admin/add-mentor', component: AddMentorComponent },
  { path: 'admin/assign-mentees', component: AssignMenteesComponent },
  { path: 'admin/view-mentors', component: ViewMentorsComponent },
  { path: 'write/complaint', component:WriteComplaintComponent },

];


