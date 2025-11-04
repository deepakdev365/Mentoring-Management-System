// models/mentor.model.ts
export interface MentorModel {
  mentorId?: number;        // Optional (auto-generated)
  fullName?: string;        // Optional in backend
  email: string;           // ✅ REQUIRED
  phoneNumber: string;     // ✅ REQUIRED
  gender?: string;         // Optional
  department?: string;     // Optional
  designation?: string;    // Optional
  password?: string;       // Optional
}