// Should be:
export interface Admin {
  adminId?: number;
  fullName: string;
  email: string;
  phoneNumber: string;
  username: string;
  password: string;
  role?: string;  // Add this field
}