import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthorizedComponent} from "./authorized.component";

const routes: Routes = [
  { path: 'authorized', component: AuthorizedComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AuthorizedRoutingModule { }
