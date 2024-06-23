import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RoleDto} from "../models/role.model";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  private url = environment.apiUrl + "/roles";

  constructor(private http: HttpClient) { }

  getAll(): Observable<RoleDto[]> {
    return this.http.get<RoleDto[]>(this.url);
  }

}
