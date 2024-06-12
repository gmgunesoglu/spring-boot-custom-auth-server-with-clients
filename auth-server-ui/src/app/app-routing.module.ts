import { AuthorizedComponent } from './components/authorized/authorized.component';
import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserComponent} from "./components/user/user.component";
import {RealmComponent} from "./components/realm/realm.component";
import {ClientComponent} from "./components/client/client.component";
import {ResourceServerComponent} from "./components/resource-server/resource-server.component";
import {RoleComponent} from "./components/role/role.component";
import {PolicyComponent} from "./components/policy/policy.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'authorized', component: AuthorizedComponent },
  { path: 'realms', component: RealmComponent },
  { path: 'clients', component: ClientComponent },
  { path: 'resource-servers', component: ResourceServerComponent },
  { path: 'users', component: UserComponent },
  { path: 'roles', component: RoleComponent },
  { path: 'policies', component: PolicyComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),

  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
