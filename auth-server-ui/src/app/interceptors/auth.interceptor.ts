import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {OauthService} from "../service/oauth.service";
import {Router} from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (sessionStorage.getItem("access_token")){
      request = request.clone({
        setHeaders: {
          'Content-Type': 'application/json',
          Authorization : `Bearer ${sessionStorage.getItem("access_token")}`
        }
      })
      console.log('Intercepted request: ', request);
      console.log('Access token: ', sessionStorage.getItem("access_token"));
    }else{
      console.log('Request sent without interceptor');
    }
    return next.handle(request);
  }

}
