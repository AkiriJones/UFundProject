import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ManagerCupboardComponent } from './Manager/manager-cupboard/manager-cupboard.component';
import { CupboardComponent } from './Helper/cupboard/cupboard.component';
import { BasketComponent } from './Helper/basket/basket.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'manager-cupboard', component: ManagerCupboardComponent},
  { path: 'cupboard', component: CupboardComponent},
  { path: 'basket', component: BasketComponent},
  { path: '', redirectTo: '/login', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
