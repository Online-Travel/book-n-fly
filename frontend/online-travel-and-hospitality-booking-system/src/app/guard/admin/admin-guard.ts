import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { inject } from '@angular/core';

export const adminGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const token = authService.getToken();
  const role = authService.getRole();

  return true;
  // if (token && role === 'ADMIN') {
  //   return true;
  // } else {
  //   router.navigate(['/home']);
  //   return false;
  // }
};
