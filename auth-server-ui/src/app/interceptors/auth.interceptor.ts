import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {OauthService} from "../service/oauth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private oauthService: OauthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (this.oauthService.accessToken != ""){
      request = request.clone({
        setHeaders: {
          'Content-Type': 'application/json',
          Authorization : `Bearer ${this.oauthService.accessToken}`
        }
      })
      console.log('Intercepted request: ', request);
      console.log('Access token: ', this.oauthService.accessToken);
    }else{
      console.log('Request send without interceptor');
    }
    return next.handle(request);
  }

}
