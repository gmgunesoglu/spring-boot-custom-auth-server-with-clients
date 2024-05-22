import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { MyFirstCompComponent } from './app/my-first-comp/my-first-comp.component';
import { config } from './app/app.config.server';

const bootstrap = () => bootstrapApplication(AppComponent, config);

export default bootstrap;
