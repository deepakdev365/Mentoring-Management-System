import { Routes } from '@angular/router';
import { LandingComponent } from './components/landing.component/landing.component';
import { AdminLoginComponent } from './components/admin-login.component/admin-login.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { adminAuthGuard } from './guards/admin-auth-guard';
import { StudentsComponent } from './components/students/students.component';
import { MentorComponent } from './components/mentor/mentor.component';
import { StudentLoginComponent } from './components/student-login.component/student-login.component';
import { studentAuthGuard } from './guards/student-auth-guard';
import { StudentLayoutComponent } from './layouts/student-layout/student-layout.component';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { AssignMenteesComponent } from './components/assign-mentees/assign-mentees.component';
import { MentorLoginComponent } from './components/mentor-login.component/mentor-login.component';
import { MentorDashboard } from './components/mentor-dashboard/mentor-dashboard';
import { ProfileComponent } from './components/profile/profile.component';

import { StudentDashboardComponent } from './components/student-dashboard/student-dashboard.component';





import { AssignStudentComponent } from './components/assign-student/assign-student.component';
import { MakeComplaintComponent } from './components/make-complaint/make-complaint.component';
import { MyComplaintsComponent } from './components/my-complaints/my-complaints.component';
import { MentorComplaintsComponent } from './components/mentor-complaints/mentor-complaints.component';
import { MentorLayoutComponent } from './layouts/mentor-layout/mentor-layout.component';
import { mentorAuthGuard } from './guards/mentor-auth-guard';
import { MentorProfileComponent } from './mentor-profile/mentor-profile.component';
import { MentorStudentsComponent } from './mentor-students/mentor-students.component';
import { StudentProfileComponent } from './components/student-profile/student-profile.component';


export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'admin-login', component: AdminLoginComponent },
  {path:'mentor-dashboard',component:MentorDashboard},
  { path: '', component: LandingComponent },

  { path: 'mentor-login', component: MentorLoginComponent },
  
   {path: 'admin',
  component: AdminLayoutComponent,
  canActivate: [adminAuthGuard],
  children: [
    { path: 'dashboard', component: AdminDashboardComponent },
    { path: 'students', component: StudentsComponent },
    
    { path: 'student/:regNo', component: StudentProfileComponent },

    { path:'assign-mentees', component: AssignMenteesComponent },
  ]
},
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



//  {
//     path: 'mentor',
//     component: MentorDashboard,
//     children: [
//       { path: 'dashboard', component: MentorDashboard }, // OR separate dashboard component
//       { path: 'students', component: StudentsComponent },
//       { path: 'profile', component: ProfileComponent },
//       { path: 'assign-student', component: AssignStudentComponent },

//       { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
//     ]
//   },

  { path: '', redirectTo: 'mentor', pathMatch: 'full' },

{ path: 'make-complaint', component: MakeComplaintComponent },
{ path: 'my-complaints', component: MyComplaintsComponent },
{ path: 'mentor-complaints', component: MentorComplaintsComponent },
{
  path: 'mentor',
  component: MentorLayoutComponent,
  canActivate: [mentorAuthGuard],
  children: [

    { path: 'dashboard', component: MentorDashboard },
    { path: 'profile', component: MentorProfileComponent },
    { path: 'students', component: MentorStudentsComponent },
    { path: 'complaints', component: MentorComplaintsComponent },

    { path: '', redirectTo: 'dashboard', pathMatch: 'full' }

  ]
}



];
