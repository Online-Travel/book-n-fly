import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { inject } from '@angular/core';

export const agentGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const token = authService.getToken();
  const role = authService.getRole();

  if (token && role === 'TRAVEL_AGENT') { // Adjust role string if needed
    return true;
  } else {
    router.navigate(['/home']);
    return false;
  }
};
