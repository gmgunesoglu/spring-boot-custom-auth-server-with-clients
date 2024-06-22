import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {UserDto} from "../../models/user.model";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  username: string = '';
  users: UserDto[] = [];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadAllUsers();
  }

  loadAllUsers(): void {
    this.userService.getAll().subscribe(users => {
      this.users = users;
    });
  }

  findUser(): void {
    if (this.username) {
      this.userService.findAllByUsername(this.username).subscribe(users => {
        this.users = users;
      });
    } else {
      this.loadAllUsers();
    }
  }

  showCreateUser(): void {
    // Implement navigation to UserRegisterContainer
  }

  showUserDetail(username: string): void {
    // Implement navigation to UserDetailContainer
  }
}
