import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, throwError} from "rxjs";
import jwt_decode from 'jwt-decode';
import {catchError, map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  token_url = environment.token_url;
  logout_url = environment.token_url;
  client_id = environment.client_id;
  client_secret = environment.client_secret;
  authorize_uri = environment.authorize_uri;
  params: any = {
    client_id: environment.client_id,
    redirect_uri: environment.redirect_uri,
    scope: environment.scope,
    response_type: environment.response_type,
    response_mode: environment.response_mode,
    code_challenge_method: environment.code_challenge_method,
    code_challenge: environment.code_challenge,
  }

  constructor(private httpClient: HttpClient) { }



  public getToken(code: string): Observable<any> {
    let body = new URLSearchParams();
    body.set('grant_type', environment.grant_type);
    body.set('client_id', environment.client_id);
    body.set('redirect_uri', environment.redirect_uri);
    body.set('scope', environment.scope);
    body.set('code_verifier', environment.code_verifier);
    body.set('code', code);
    const basic_auth = 'Basic ' + btoa(this.client_id + ':' + this.client_secret);
    const headers_object = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Accept': '*/*',
      'Authorization': basic_auth
    });
    const httpOptions = { headers: headers_object };

    return this.httpClient.post<any>(this.token_url, body, httpOptions)
      .pipe(
        map(data => {
          if (this.isSuperUser(data.access_token)) {
            sessionStorage.setItem("auth_token", code);
            sessionStorage.setItem("access_token", data.access_token);
            sessionStorage.setItem("id_token", data.id_token);
            sessionStorage.setItem("refresh_token", data.refresh_token);
            return data;
          } else {
            throw new Error("Logged user is not SUPER_USER");
          }
        }),
        catchError(error => {
          console.error("Error occurred while fetching token", error);
          return throwError(error);
        })
      );
  }

  private decodeToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (error) {
      console.error('Invalid token');
      return null;
    }
  }

  private isSuperUser(token: string): boolean {
    const decodedToken = this.decodeToken(token);
    console.log("decoded token: ", decodedToken);
    console.log("decoded token authorities: ", decodedToken.authorities);
    if (decodedToken && decodedToken.authorities) {
      return decodedToken.authorities.includes('SUPER_USER');
    }
    return false;
  }

  public login(): void {
    const httpParams = new HttpParams({fromObject: this.params});
    const codeUrl = this.authorize_uri + httpParams.toString();
    window.location.href = codeUrl;
  }

  public logout(): void {
    sessionStorage.clear();
    localStorage.clear();
    this.clearCookies();
    this.login();
  }

  private clearCookies() {
    const cookies = document.cookie.split(";");
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i];
      const eqPos = cookie.indexOf("=");
      const name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
      document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;domain=" + window.location.hostname;
    }
  }
}
