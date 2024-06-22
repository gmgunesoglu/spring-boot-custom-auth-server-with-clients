import { Component, OnInit } from '@angular/core';
import {UserDetailDto} from "../../models/user.model";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  userDetailDto: UserDetailDto | null = null;
  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    const username = this.route.snapshot.paramMap.get('username');
    if (username) {
      this.userService.get(username).subscribe(user => {
        this.userDetailDto = user;
      });
    }
  }

  blockUser(): void {
    // Implement block user functionality
  }

  unblockUser(): void {
    // Implement unblock user functionality
  }

  setPassword(): void {
    // Implement set password functionality
  }

  setRoles(): void {
    // Implement set roles functionality
  }

  goBack(): void {
    // Implement navigation back to UserContainer
  }

}
