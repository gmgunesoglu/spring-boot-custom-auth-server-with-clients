import {Component, OnInit, TemplateRef} from '@angular/core';
import {UserService} from "../../service/user.service";
import {UserDto, UserRegisterDto} from "../../models/user.model";
import {Router} from "@angular/router";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {RoleService} from "../../service/role.service";
import {RoleDto} from "../../models/role.model";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  username: string = '';
  users: UserDto[] = [];
  newUser: UserRegisterDto = { username: '', password: '', roles: [], clientName: '' };
  roles: RoleDto[] = [];
  selectedRoles: Set<string> = new Set();
  private modalRef: NgbModalRef | null = null;
  errorMessage: string | null = null;

  constructor(
    private userService: UserService,
    private router: Router,
    private modalService: NgbModal,
    private roleService: RoleService
  ) {}

  ngOnInit(): void {
    this.loadAllUsers();
    this.loadRoles();
  }

  loadAllUsers(): void {
    this.userService.getAll().subscribe(
      users => {
        this.users = users;
      },
      error => {
        this.handleError(error);
      }
    );
  }

  loadRoles(): void {
    this.roleService.getAll().subscribe(
      roles => {
        this.roles = roles;
      },
      error => {
        this.handleError(error);
      }
    );
  }

  findUser(): void {
    if (this.username) {
      this.userService.findAllByUsername(this.username).subscribe(
        users => {
          this.users = users;
        },
        error => {
          this.handleError(error);
        }
      );
    } else {
      this.loadAllUsers();
    }
  }

  showUserDetail(username: string): void {
    this.router.navigate(['/users', username]);
  }

  openCreateUserModal(createUserTemplate: TemplateRef<any>): void {
    this.errorMessage = null;
    this.modalRef = this.modalService.open(createUserTemplate);
    this.modalRef.result.then(
      result => {
        this.registerUser();
      },
      reason => {
        this.selectedRoles.clear();
      }
    );
  }

  toggleRole(role: string): void {
    if (this.selectedRoles.has(role)) {
      this.selectedRoles.delete(role);
    } else {
      this.selectedRoles.add(role);
    }
  }

  registerUser(): void {
    this.newUser.roles = Array.from(this.selectedRoles);
    this.userService.add(this.newUser).subscribe(
      () => {
        this.loadAllUsers();
        this.selectedRoles.clear();
        this.newUser = { username: '', password: '', roles: [], clientName: '' };
        if (this.modalRef) {
          this.modalRef.close();
          this.modalRef = null;
        }
        this.errorMessage = null;
      },
      error => {
        this.handleError(error);
      }
    );
  }

  private handleError(error: any): void {
    if (error && error.error && error.error.message) {
      this.errorMessage = error.error.message;
    } else {
      this.errorMessage = 'An unexpected error occurred.';
    }
    console.error('Error:', error);
  }
}
