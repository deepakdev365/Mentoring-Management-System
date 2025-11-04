import { MentorModel } from './mentor.model';

export interface StudentModel {
  registrationNumber?: number;
  fullName: string;
  fatherGuardianName: string;
  email: string;
  dob: string;
  gender: string;
  nationality: string;
  religion: string;
  emergencyContact: string;
  phoneNumber: string;
  localAddress: string;
  permanentAddress: string;
  city: string;
  state: string;
  zipCode: string;
  admissionNumber: string;
  applicationNumber: string;
  feeCategory: string;
  dateOfAdmission: string;
  program: string;
  branch: string;
  semester: string;
  rollNo: string;
  eligibilityNumber: string;
  prnNo: string;
  batch: string;
  department: string;
  password: string;
   mentor?: MentorModel;
}