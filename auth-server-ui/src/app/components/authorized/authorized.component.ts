import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {OauthService} from "../../service/oauth.service";
import {AuthInterceptor} from "../../interceptors/auth.interceptor";

@Component({
  selector: 'app-authorized',
  templateUrl: './authorized.component.html',
  styleUrls: ['./authorized.component.css']
})
export class AuthorizedComponent implements OnInit {

  code = "";


  constructor(
    private activatedRoute: ActivatedRoute,
    private oauthService: OauthService,
    // private authInterceptor: AuthInterceptor
  ) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe( data => {
      this.code = data.code;
      this.getToken();
    });
  }

  private getToken(): void {
    this.oauthService.getToken(this.code);
  }

}
