import { Component, OnInit } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public auth_code: string | null = sessionStorage.getItem("auth_token");
  public access_token: string | null = sessionStorage.getItem("access_token");
  public id_token: string | null = sessionStorage.getItem("id_token");
  public refresh_token: string | null = sessionStorage.getItem("refresh_token");



  constructor(
    private router: Router
  ) {}

  ngOnInit(): void {

    console.log("Home Component Init: ")
    this.access_token = sessionStorage.getItem("access_token");
    console.log("access_token: " + this.auth_code);
    this.id_token = sessionStorage.getItem("id_token");
    console.log("id_token: " + this.id_token);
    this.refresh_token = sessionStorage.getItem("refresh_token");
    console.log("refresh_token: " + this.refresh_token);
  }



}
