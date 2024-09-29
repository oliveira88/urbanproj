import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ManterObraComponent } from './manter-obra/manter-obra.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {
    path: 'login',
    component: LoginComponent,
  },
  { path: 'home', component: ManterObraComponent },
];
