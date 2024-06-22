import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, throwError} from "rxjs";
import jwt_decode from 'jwt-decode';
import {catchError, map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class OauthService {

  token_url = environment.token_url;
  logout_url = environment.token_url;

  constructor(private httpClient: HttpClient) { }



  public getToken(code: string): Observable<any> {
    let body = new URLSearchParams();
    body.set('grant_type', environment.grant_type);
    body.set('client_id', environment.client_id);
    body.set('redirect_uri', environment.redirect_uri);
    body.set('scope', environment.scope);
    body.set('code_verifier', environment.code_verifier);
    body.set('code', code);
    const basic_auth = 'Basic ' + btoa('client:secret');
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

  public logout(): Observable<any> {
    const params = new HttpParams().set('id_token_hint', sessionStorage.getItem('id_token') ?? '');
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
    sessionStorage.clear();
    localStorage.clear();
    this.clearCookies();
    return this.httpClient.get(this.logout_url, { params, headers }).pipe(
      catchError(error => {
        console.error("Error during logout", error);
        return throwError(error);
      })
    );
  }

  private  clearCookies() {
    // Tüm cookie'leri listelemek
    const cookies = document.cookie.split(";");

    // Her bir cookie'yi silmek
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i];
      const eqPos = cookie.indexOf("=");
      const name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
      document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
  }
}
