import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {UserDetailDto, UserDto, UserRegisterDto} from "../models/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = environment.apiUrl + "/users";

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get<any>(this.url);
  }

  findAllByUsername(username: string): Observable<UserDto[]> {
    return this.http.get<UserDto[]>(`${this.url}/list/${username}`);
  }

  get(username: string): Observable<UserDetailDto> {
    return this.http.get<UserDetailDto>(`${this.url}/${username}`);
  }

  add(userRegisterDto: UserRegisterDto): Observable<string> {
    return this.http.post<string>(this.url, userRegisterDto);
  }

  block(username: string | undefined): Observable<UserDetailDto> {
    const url = `${this.url}/block/${username}`;
    return this.http.post<UserDetailDto>(url, {});
  }
  unblock(username: string | undefined): Observable<UserDetailDto> {
    const url = `${this.url}/unblock/${username}`;
    return this.http.post<UserDetailDto>(url, {});
  }

  setRoles(username: string, selectedRolesArray: string[]): Observable<UserDetailDto> {
    const url = `${this.url}/set-roles/${username}`;
    const body = {
      roles: selectedRolesArray
    };
    return this.http.post<UserDetailDto>(url, body);
  }
}
