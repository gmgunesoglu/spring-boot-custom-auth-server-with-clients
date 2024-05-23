import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {MessageDetailsComponent} from "../message-details/message-details.component";

@Component({
  selector: 'app-my-first-comp',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    MessageDetailsComponent
  ],
  templateUrl: './my-first-comp.component.html',
  styleUrl: './my-first-comp.component.scss'
})
export class MyFirstCompComponent {

  name: string = 'Gokhan';
  email: string = 'gmgunesoglu@mail.com';
  message: string = 'hello there';
  isSubmitted: boolean = false;
  messages: Array<any> = []

  onClick(): void {
    // console.log("name: ", this.name, "\nemail: ", this.email, "\nmessage: ", this.message);
    this.isSubmitted = true;
    this.messages.push({
      'name': this.name,
      'email': this.email,
      'message': this.message
    });
  }

  deleteMessage(index: number):void {
    this.messages.splice(index, 1)
  }
}




