import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-my-first-comp',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './my-first-comp.component.html',
  styleUrl: './my-first-comp.component.scss'
})
export class MyFirstCompComponent {

  name: string = 'Gokhan';
  email: string = 'gmgunesoglu@mail.com';
  message: string = 'hello there';
  isSubmit: boolean = false;

  onClick(): void {
    console.log("name: ", this.name, "\nemail: ", this.email, "\nmessage: ", this.message);
    this.isSubmit = true;
  }
}




