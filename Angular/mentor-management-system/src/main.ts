import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
import { routes } from './app/app.routes';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';  // ✅ add this import



bootstrapApplication(App, {
  providers: [
    provideRouter(routes),
    provideHttpClient() // ✅ This is the missing piece
  ]
});