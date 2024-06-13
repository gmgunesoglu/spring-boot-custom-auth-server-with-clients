import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OauthService {

  token_url = environment.token_url;

  accessToken: string = "";
  idToken: string = "";
  refreshToken: string = "";
  loggedIn: boolean = false;

  constructor(private httpClient: HttpClient) { }

  public getToken(code: string): Observable<any> {
    let body = new URLSearchParams();
    body.set('grant_type', environment.grant_type);
    body.set('client_id', environment.client_id);
    body.set('redirect_uri', environment.redirect_uri);
    body.set('scope', environment.scope);
    body.set('code_verifier', environment.code_verifier);
    body.set('code', code);
    const basic_auth = 'Basic '+ btoa('client:secret');
    const headers_object = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Accept': '*/*',
      'Authorization': basic_auth
    });
    const httpOptions = { headers: headers_object};
    const response =  this.httpClient.post<any>(this.token_url, body, httpOptions);

    response.subscribe(
      data => {
        this.idToken = data.id_token;
        this.accessToken = data.access_token;
        this.refreshToken = data.refresh_token;
        // this.authInterceptor.setAccessToken(this.accessToken);
        console.log("from service...");
        console.log("access token", this.accessToken);
        console.log("id token", this.idToken);
        console.log("refresh token", this.refreshToken);
        // this.authInterceptor.setAccessToken(this.accessToken);
        console.log(data);
        this.loggedIn = true;
      },
      error => {
        this.loggedIn = false;
        console.log(error);
      }
    )
    return response;
  }
}
