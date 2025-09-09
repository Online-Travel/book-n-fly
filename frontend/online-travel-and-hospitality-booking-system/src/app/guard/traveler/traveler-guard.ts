import { CanActivateFn } from '@angular/router';

export const travelerGuard: CanActivateFn = (route, state) => {
  return true;
};
