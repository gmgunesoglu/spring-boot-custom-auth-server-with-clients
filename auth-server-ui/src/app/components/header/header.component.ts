import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../../service/auth.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  loginUri: string = environment.apiUrl+"/login";

  constructor(
    private router: Router,
    private oauthService: AuthService
    ) {}

  ngOnInit(): void {
  }

  logout() {

    this.oauthService.logout();
  }
}
