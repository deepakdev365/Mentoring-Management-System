import { Routes } from '@angular/router';
import { LandingComponent } from './components/landing.component/landing.component';
import { AdminLoginComponent } from './components/admin-login.component/admin-login.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { adminAuthGuard } from './guards/admin-auth-guard';
import { StudentsComponent } from './components/students/students.component';
import { MentorComponent } from './components/mentor/mentor.component';
import { StudentLoginComponent } from './components/student-login.component/student-login.component';
import { StudentDashboardComponent } from './components/student-dashboard/student-dashboard.component';
import { studentAuthGuard } from './guards/student-auth-guard';
import { StudentLayoutComponent } from './layouts/student-layout/student-layout.component';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { AssignMenteesComponent } from './components/assign-mentees/assign-mentees.component';
import { MentorLoginComponent } from './components/mentor-login.component/mentor-login.component';
import { StudentProfileComponent } from './components/student-profile/student-profile.component';



export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'admin-login', component: AdminLoginComponent },

  { path: 'mentor-login', component: MentorLoginComponent },
  
   {path: 'admin',
  component: AdminLayoutComponent,
  canActivate: [adminAuthGuard],
  children: [
    { path: 'dashboard', component: AdminDashboardComponent }
  ]
},
{ path: 'students', component: StudentsComponent },
{ path: 'mentor', component: MentorComponent },
  { path: 'student-login', component: StudentLoginComponent },
  
{
  path: 'student',
  component: StudentLayoutComponent,
  canActivate: [studentAuthGuard],
  children: [
    { path: 'dashboard', component: StudentDashboardComponent }
  ]
},

{ path:'admin/assign-mentees', component: AssignMenteesComponent },

// {path:'admin/student/regNo', component:StudentProfileComponent},
{
  path: 'admin',
  children: [
    { path: 'students', component: StudentsComponent },
    { path: 'student/:regNo', component: StudentProfileComponent }
  ]
}
];
