import { CanActivateFn } from '@angular/router';

export const hotelManagerGuard: CanActivateFn = (route, state) => {
  return true;
};
