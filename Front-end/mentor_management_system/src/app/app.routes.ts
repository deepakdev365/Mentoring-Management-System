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
import { StudentDashboardComponent } from './components/student-dashboard/student-dashboard.component';
import { MakeComplaintComponent } from './components/make-complaint/make-complaint.component';
import { MyComplaintsComponent } from './components/my-complaints/my-complaints.component';
import { MentorComplaintsComponent } from './components/mentor-complaints/mentor-complaints.component';
import { MentorLayoutComponent } from './layouts/mentor-layout/mentor-layout.component';
import { mentorAuthGuard } from './guards/mentor-auth-guard';
import { MentorProfileComponent } from './mentor-profile/mentor-profile.component';
import { MentorStudentsComponent } from './mentor-students/mentor-students.component';
import { MentorStudentDetailComponent } from './mentor-student-detail/mentor-student-detail.component';
import { StudentProfileComponent } from './components/student-profile/student-profile.component';
import { ReportCardComponent } from './components/report-card/report-card.component';
import { MakeAnnouncementComponent } from './components/make-announcement/make-announcement.component';
import { StudentNotificationsComponent } from './components/student-notifications/student-notifications.component';
import { MarksUploadComponent } from './components/marks-upload/marks-upload.component';
import { UploadPaymentsComponent } from './components/upload-payments/upload-payments.component';
import { UploadAttendanceComponent } from './components/upload-attendance/upload-attendance.component';
import { UploadSubjectRegistrationComponent } from './components/upload-subject-registration/upload-subject-registration.component';
import { AskLeavePermissionComponent } from './components/ask-leave-permission/ask-leave-permission.component';
import { LeavePermissionsComponent } from './components/leave-permissions/leave-permissions.component';
import { StudentPaymentHistoryComponent } from './components/student-payment-history/student-payment-history.component';
import { StudentSubjectsComponent } from './components/student-subjects/student-subjects.component';
import { MentorLeavePermissionsComponent } from './components/mentor-leave-permissions/mentor-leave-permissions.component';
import { MentorLeaveSlipComponent } from './components/mentor-leave-slip/mentor-leave-slip.component';
import { MentorStudentBacklogsComponent } from './components/mentor-student-backlogs/mentor-student-backlogs';
import { MentorLowAttendanceComponent } from './components/mentor-low-attendance/mentor-low-attendance';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'admin-login', component: AdminLoginComponent },
  { path: 'mentor-login', component: MentorLoginComponent },
  { path: 'student-login', component: StudentLoginComponent },
  
  {
    path: 'admin',
    component: AdminLayoutComponent,
    canActivate: [adminAuthGuard],
    children: [
      { path: 'dashboard', component: AdminDashboardComponent },
      { path: 'students', component: StudentsComponent },
      { path: 'student/:regNo', component: StudentProfileComponent },
      { path: 'student/:regNo/report-card', component: ReportCardComponent },
      { path: 'assign-mentees', component: AssignMenteesComponent },
      { path: 'upload-marks', component: MarksUploadComponent },
      { path: 'upload-payments', component: UploadPaymentsComponent },
      { path: 'upload-attendance', component: UploadAttendanceComponent },
      { path: 'upload-subject-registration', component: UploadSubjectRegistrationComponent },
      { path: 'mentor', component: MentorComponent },
      { path: 'mentor/profile/:email', component: MentorProfileComponent },
      { path: 'make-announcement', component: MakeAnnouncementComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  {
    path: 'student',
    component: StudentLayoutComponent,
    canActivate: [studentAuthGuard],
    children: [
      { path: 'dashboard', component: StudentDashboardComponent },
      { path: 'report-card', component: ReportCardComponent },
      { path: 'my-complaints', component: MyComplaintsComponent },
      { path: 'make-complaint', component: MakeComplaintComponent },
      { path: 'notifications', component: StudentNotificationsComponent },
      { path: 'payments', component: StudentPaymentHistoryComponent },
      { path: 'subjects', component: StudentSubjectsComponent },
      { path: 'ask-leave-permission', component: AskLeavePermissionComponent },
      { path: 'leave-permissions', component: LeavePermissionsComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  {
    path: 'mentor',
    component: MentorLayoutComponent,
    canActivate: [mentorAuthGuard],
    children: [
      { path: 'dashboard', component: MentorDashboard },
      { path: 'profile', component: MentorProfileComponent },
      { path: 'students', component: MentorStudentsComponent },
      { path: 'student/:regNo', component: MentorStudentDetailComponent },
      { path: 'complaints', component: MentorComplaintsComponent },
      { path: 'leave-permissions', component: MentorLeavePermissionsComponent },
      { path: 'leave-slip/:id', component: MentorLeaveSlipComponent },
      { path: 'student-backlogs', component: MentorStudentBacklogsComponent },
      { path: 'low-attendance', component: MentorLowAttendanceComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  { path: 'mentor-complaints', component: MentorComplaintsComponent },
  { path: 'make-complaint', component: MakeComplaintComponent },
  { path: 'my-complaints', component: MyComplaintsComponent },
  { path: '**', redirectTo: '' }
];
