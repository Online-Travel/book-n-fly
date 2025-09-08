import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { TokenInterceptor } from './traveller/services/token-interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    // provideBrowserGlobalErrorListeners(),
    // provideHttpClient(),
    // provideZoneChangeDetection({ eventCoalescing: true }),
    // provideRouter(routes), provideClientHydration(withEventReplay())

    provideRouter(routes),
    provideHttpClient(withInterceptors([TokenInterceptor]))
  ]
};
