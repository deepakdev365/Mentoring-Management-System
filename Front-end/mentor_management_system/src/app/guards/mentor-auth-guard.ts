import { CanActivateFn } from '@angular/router';

export const mentorAuthGuard: CanActivateFn = (route, state) => {
  return true;
};
