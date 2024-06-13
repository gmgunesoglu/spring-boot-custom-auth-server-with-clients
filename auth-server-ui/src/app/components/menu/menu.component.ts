import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { HttpParams } from '@angular/common/http';
import {OauthService} from "../../service/oauth.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  loggedIn = false;

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

  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loggedIn = !!localStorage.getItem('loggedIn');
  }

  onLogin(): void {
    const httpParams = new HttpParams({fromObject: this.params});
    const codeUrl = this.authorize_uri + httpParams.toString();
    this.loggedIn = true
    localStorage.setItem('loggedIn', 'true');
    location.href = codeUrl;

  }

  onLogout(): void {
    console.log('soon');
    this.loggedIn = false;
    localStorage.removeItem('loggedIn');
  }

}
