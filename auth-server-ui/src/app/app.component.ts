import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {environment} from "../environments/environment";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'Auth-Server-UI';
  sideBarOpen = true;
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

  ngOnInit(): void {
    // if(!sessionStorage.getItem("auth_token")){
    //   this.onLogin();
    // }
  }

  // private checkAccessToken(): void {
  //   const accessToken = sessionStorage.getItem("access_token");
  //   if (!accessToken) {
  //     this.onLogin();
  //   }
  // }

  public onLogin(): void {
    const httpParams = new HttpParams({fromObject: this.params});
    const codeUrl = this.authorize_uri + httpParams.toString();
    location.href = codeUrl;
  }

  sideBarToggler() {
    this.sideBarOpen = !this.sideBarOpen;
  }
}
