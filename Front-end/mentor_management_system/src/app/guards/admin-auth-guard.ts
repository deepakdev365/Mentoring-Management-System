import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const adminAuthGuard: CanActivateFn = () => {

  const router = inject(Router);

  const loggedIn = sessionStorage.getItem('adminLoggedIn');

  if (loggedIn === 'true') {
    return true;
  } else {
    router.navigate(['/admin-login']);
    return false;
  }

};