import {Component, OnInit, TemplateRef} from '@angular/core';
import {UserDetailDto} from "../../models/user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {NgbModal, ModalDismissReasons} from "@ng-bootstrap/ng-bootstrap";
import {RoleService} from "../../service/role.service";
import {RoleDto} from "../../models/role.model";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  roles: RoleDto[] = [];
  selectedRoles: Set<string> = new Set();

  closeResult: string = "";

  userDetailDto: UserDetailDto | null = null;
  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router,
    private modalService: NgbModal,
    private roleService: RoleService
  ) {}

  ngOnInit(): void {
    const username = this.route.snapshot.paramMap.get('username');
    if (username) {
      this.userService.get(username).subscribe(user => {
        this.userDetailDto = user;
      });
    }
  }

  block(): void {
    this.userService.block(this.userDetailDto?.username).subscribe(user => {
      this.userDetailDto = user;
    });
  }

  unblock(): void {
    this.userService.unblock(this.userDetailDto?.username).subscribe(user => {
      this.userDetailDto = user;
    });
  }

  setPassword(): void {
    // Implement set password functionality
  }


  goBack(): void {
    this.router.navigate(['/users']);
  }

  open(rolesTemplate: TemplateRef<any>) {
    this.roleService.getAll().subscribe(roles => {
      this.roles = roles;
      this.modalService.open(rolesTemplate, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
        this.closeResult = `Closed with: ${result}`;
        this.saveRoles();
      }, (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      });
    });
  }

  toggleRole(role: string): void {
    if (this.selectedRoles.has(role)) {
      this.selectedRoles.delete(role);
    } else {
      this.selectedRoles.add(role);
    }
  }

  saveRoles(): void {
    if (!this.userDetailDto) {
      console.error('userDetailDto is null or undefined');
      return;
    }
    const selectedRolesArray: string[] = Array.from(this.selectedRoles);
    this.selectedRoles.clear();
    console.log('Selected Roles:', selectedRolesArray);
    this.userService.setRoles(this.userDetailDto.username, selectedRolesArray)
      .subscribe(
        updatedUser => {
          // this.userDetailDto = updatedUser; // Örneğin, güncelleme işlemi
          this.userService.get(updatedUser.username).subscribe(
            result => {
              this.userDetailDto = result;
            },
            error => {
              console.error('Get userDetailDto failed:', error);
            }
          )
        },
        error => {
          console.error('Failed to update roles:', error);
        }
      );
  }

  private getDismissReason(reason: any): string {
    this.selectedRoles.clear();
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }
}
