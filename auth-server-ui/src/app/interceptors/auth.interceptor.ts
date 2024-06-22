import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse, HttpParams, HttpHeaders, HttpClient, HttpContext, HttpContextToken
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {OauthService} from "../service/oauth.service";
import {Router} from "@angular/router";
import {catchError, mergeMap} from "rxjs/operators";


const SKIP_INTERCEPTOR = new HttpContextToken(() => false);


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  refreshTokenUrl = environment.refreshTokenUrl;
  clientId = environment.client_id;
  clientSecret = environment.client_secret;

  constructor(
    private router: Router,
    private http: HttpClient
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Refresh token isteğini atla
    if (request.context.get(SKIP_INTERCEPTOR)) {
      return next.handle(request);
    }

    if (sessionStorage.getItem('access_token')) {
      request = request.clone({
        setHeaders: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${sessionStorage.getItem('access_token')}`
        }
      });
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 480) {
          console.log('Trying to refresh token');
          return this.refreshToken().pipe(
            mergeMap((tokenResponse: any) => {
              console.log('Token response:', tokenResponse); // Refresh token cevabını konsola yazdır

              // Yeni token'ları sessionStorage'a kaydet
              sessionStorage.setItem('access_token', tokenResponse.access_token);
              sessionStorage.setItem('refresh_token', tokenResponse.refresh_token);

              // Yeniden gönderilecek isteği güncel token ile tekrar gönder
              const authReq = request.clone({
                setHeaders: {
                  Authorization: `Bearer ${tokenResponse.access_token}`
                }
              });
              return next.handle(authReq);
            }),
            catchError(refreshError => {
              // Token refresh başarısız olursa burada işlemler yapılabilir
              console.error('Token refresh failed', refreshError)
              sessionStorage.clear();
              localStorage.clear();
              window.location.reload();
              return throwError(refreshError);
            })
          )
        } else {
          // Diğer hatalar için hatayı tekrar fırlat
          return throwError(error);
        }
      })
    );
  }

  private refreshToken(): Observable<any> {
    const refreshToken: string = sessionStorage.getItem('refresh_token') ?? '';
    const body = new HttpParams()
      .set('grant_type', 'refresh_token')
      .set('refresh_token', refreshToken);

    const headers = new HttpHeaders()
      .set('Content-Type', 'application/x-www-form-urlencoded')
      .set('Authorization', 'Basic ' + btoa(this.clientId + ":" + this.clientSecret)); // Basic Authentication header

    const requestOptions = {
      headers: headers,
      context: new HttpContext().set(SKIP_INTERCEPTOR, true)
    };

    return this.http.post(this.refreshTokenUrl, body.toString(), requestOptions).pipe(
      catchError(error => {
        console.error('Error refreshing token', error);
        return throwError(error); // Bu satır throw error methodu ile uyumlu hale getirildi.
      })
    );
  }

}
