import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { UserDto, UserRegisterDto } from '../../models/user.model';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  users: UserDto[] = [];
  selectedUser: UserDto | null = null;
  newUser: UserRegisterDto = {
    username: '',
    password: '',
    roles: [],
    clientName: ''
  };

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  getAll(): void {
    this.userService.getAll().subscribe(users => {
      this.users = users;
    });
  }
}
