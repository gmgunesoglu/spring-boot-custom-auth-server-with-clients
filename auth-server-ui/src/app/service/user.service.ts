import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import { UserDto, UserRegisterDto } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<UserDto[]> {
    return this.http.get<UserDto[]>(this.url);
  }

}
