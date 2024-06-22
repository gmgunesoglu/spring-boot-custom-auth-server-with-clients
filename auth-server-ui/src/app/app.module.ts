import { BrowserModule } from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpParams} from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { MenuComponent } from './components/menu/menu.component';
import { HeaderComponent } from './components/header/header.component';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { FooterComponent } from './components/footer/footer.component';
import { AccountComponent } from './components/account/account.component';
import { RealmComponent } from './components/realm/realm.component';
import { ClientComponent } from './components/client/client.component';
import { ResourceServerComponent } from './components/resource-server/resource-server.component';
import { UserComponent } from './components/user/user.component';
import { RoleComponent } from './components/role/role.component';
import { PolicyComponent } from './components/policy/policy.component';
import {AuthInterceptor} from "./interceptors/auth.interceptor";
import { UserRegisterComponent } from './components/user-register/user-register.component';
import { UserDetailComponent } from './components/user-detail/user-detail.component';
import {FormsModule} from "@angular/forms";
import {AuthorizedRoutingModule} from "./components/authorized/authorized-routing.module";
import {DashboardRoutingModule} from "./components/dashboard/dashboard-routing.module";
import { AuthorizedComponent } from './components/authorized/authorized.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MenuComponent,
    HeaderComponent,
    SidenavComponent,
    DashboardComponent,
    FooterComponent,
    AccountComponent,
    RealmComponent,
    ClientComponent,
    ResourceServerComponent,
    UserComponent,
    RoleComponent,
    PolicyComponent,
    UserRegisterComponent,
    UserDetailComponent,
    AuthorizedComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatMenuModule,
    MatIconModule,
    MatDividerModule,
    MatListModule,
    FormsModule,
    AuthorizedRoutingModule,
    DashboardRoutingModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {}
