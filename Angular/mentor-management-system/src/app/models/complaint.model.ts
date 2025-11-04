import { StudentModel } from './student.model';
import { MentorModel } from './mentor.model';

// Should be:
export interface ComplaintModel {
  complaintId?: number;
  length?: number;
  title: string;
  description: string;
  status?: string;
  dateCreated?: Date;  // Change to Date type
  student?: StudentModel;
  mentor?: MentorModel;
}