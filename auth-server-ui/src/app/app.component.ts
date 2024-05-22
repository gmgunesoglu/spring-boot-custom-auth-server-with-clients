import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Title} from "@angular/platform-browser";
import {MyFirstCompComponent} from "./my-first-comp/my-first-comp.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MyFirstCompComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'developer: gmgunesoglu :)\nMy First Angular Project.';
  protected readonly Title = Title;
}
