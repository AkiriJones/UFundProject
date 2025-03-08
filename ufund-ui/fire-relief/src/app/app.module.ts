import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { BasketComponent } from './Helper/basket/basket.component';
import { ManagerCupboardComponent } from './Manager/manager-cupboard/manager-cupboard.component';
import { CupboardComponent } from './Helper/cupboard/cupboard.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    BasketComponent,
    ManagerCupboardComponent,
    CupboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    provideClientHydration(withEventReplay())
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
