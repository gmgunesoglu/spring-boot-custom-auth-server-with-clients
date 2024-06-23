import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-authorized',
  templateUrl: './authorized.component.html',
  styleUrls: ['./authorized.component.css']
})
export class AuthorizedComponent implements OnInit {

  code = "";
  authorize_uri = environment.authorize_uri;
  loginUrl = environment.apiUrl + "/login";

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
    private oauthService: AuthService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe( data => {
      this.getToken(data.code);
    });
  }

  private getToken(code: string): void {
    this.oauthService.getToken(code).subscribe(
      data => {
        // Başarılı yanıt durumunda yapılacak işlemler
        this.code = "Login success, redirecting...";
        setTimeout(() => {
          this.router.navigate(['']);
        }, 2000);
      },
      error => {
        // Hata durumunda yapılacak işlemler
        this.code = "Only Admin can login here!";
        sessionStorage.clear();
        localStorage.clear();
        setTimeout(() => {
          location.href = this.loginUrl;
        }, 2000);
      }
    );
  }
}
