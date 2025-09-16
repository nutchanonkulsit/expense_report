// import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
// import { isPlatformBrowser } from '@angular/common';
// import {
//   HttpEvent,
//   HttpHandler,
//   HttpInterceptor,
//   HttpRequest,
//   HttpErrorResponse,
// } from '@angular/common/http';
// import { Observable, throwError } from 'rxjs';
// import { catchError } from 'rxjs/operators';
// import { Router } from '@angular/router';

// @Injectable()
// export class AuthInterceptor implements HttpInterceptor {
//   constructor(
//     private router: Router,
//     @Inject(PLATFORM_ID) private platformId: Object
//   ) {}

//   intercept(
//     req: HttpRequest<any>,
//     next: HttpHandler
//   ): Observable<HttpEvent<any>> {
//     let authReq = req;

//     if (isPlatformBrowser(this.platformId)) {
//       const token = localStorage.getItem('token');
//       console.log(token);

//       if (token) {
//         authReq = req.clone({
//           setHeaders: {
//             Authorization: `Bearer ${token}`,
//           },
//         });
//       }
//     }

//     return next.handle(authReq).pipe(
//       catchError((err: HttpErrorResponse) => {
//         if (
//           isPlatformBrowser(this.platformId) &&
//           (err.status === 401 || err.status === 403)
//         ) {
//           console.warn('Auth error, redirecting to login:', err);
//           localStorage.removeItem('token');
//           this.router.navigate(['/login']);
//         }
//         return throwError(() => err);
//       })
//     );
//   }
// }

import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { isPlatformBrowser } from '@angular/common';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private router: Router,
    private authService: AuthService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    let token: string | null = null;

    if (isPlatformBrowser(this.platformId)) {
      token = localStorage.getItem('token');
    }
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // Token is invalid or expired, redirect to login
          this.authService.logout();
          this.router.navigate(['/login']);
          return throwError(() => error);
        }
        return throwError(() => error);
      })
    );
  }
}
