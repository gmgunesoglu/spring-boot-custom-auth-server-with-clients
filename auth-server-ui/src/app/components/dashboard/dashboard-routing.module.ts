import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RealmComponent} from "../realm/realm.component";
import {ClientComponent} from "../client/client.component";
import {ResourceServerComponent} from "../resource-server/resource-server.component";
import {UserComponent} from "../user/user.component";
import {RoleComponent} from "../role/role.component";
import {PolicyComponent} from "../policy/policy.component";
import {UserRegisterComponent} from "../user-register/user-register.component";
import {UserDetailComponent} from "../user-detail/user-detail.component";
import {HomeComponent} from "../home/home.component";
import {DashboardComponent} from "./dashboard.component";

const routes: Routes = [
  { path: '', component: DashboardComponent,
  children: [
    { path: '', component: HomeComponent },
    { path: 'realms', component: RealmComponent },
    { path: 'clients', component: ClientComponent },
    { path: 'resource-servers', component: ResourceServerComponent },
    { path: 'users', component: UserComponent },
    { path: 'users/:username', component: UserDetailComponent },
    { path: 'roles', component: RoleComponent },
    { path: 'policies', component: PolicyComponent },
    { path: 'register', component: UserRegisterComponent },
    { path: '**', redirectTo: '', pathMatch: 'full' }
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
