import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const studentAuthGuard: CanActivateFn = (route, state) => {

  const router = inject(Router);

  const loggedIn = sessionStorage.getItem("studentLoggedIn");

  if(loggedIn === "true"){
    return true;
  }

  router.navigate(['/student-login']);
  return false;

};