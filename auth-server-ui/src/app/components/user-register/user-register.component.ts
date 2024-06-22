import { Component, OnInit } from '@angular/core';
import {UserRegisterDto} from "../../models/user.model";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})

export class UserRegisterComponent implements OnInit {

  userRegisterDto: UserRegisterDto = {
    username: "",
    password: "",
    roles: [],
    clientName: ""
  }

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  saveUser(): void {
    if (this.userRegisterDto){
      this.userService.add(this.userRegisterDto).subscribe(response => {
        console.log('User created:', response);
        // Stay in the same container or show a success message
      });
    }
  }

  goBack(): void {
    // Implement navigation back to UserContainer
  }

}
