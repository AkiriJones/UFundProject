import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username: string = '';

  constructor(private router: Router, private userService: UserService) {}

  /**
   * Upon logging in, the username entered is evaluated to see if it already exists or if a new user needs to be added to the system. 
   */
  onSubmit(): void {
    if(!this.username.trim()) return; //preventing empty username submission

    //saves username value in the browser's local storage, which persists after the page is refreshed or the browser is closed.
    localStorage.setItem("username", this.username)

    //check if user exists already
    this.userService.getUser(this.username).subscribe(user => {
      if(!user) {
        //create new user if they don't exist
        this.userService.addUser(this.username).subscribe(newUser => {
          if(newUser) {
            this.redirectUser(newUser.name);
          }
        });
      }
      else {
        this.userService.setCurrentUser(user);
        this.redirectUser(user.name); 
      }
    })
  }

  /**
   * Handles logic for determining which cupboard view to route the user to, depending on whether they are a manager ("admin") or a helper.
   * 
   * @param username username input in login field
   */
  private redirectUser(username: string) {
    if(this.username === 'admin') {
      this.router.navigate(['/manager-cupboard']);
    }
    else {
      this.router.navigate(['/cupboard']);
    }
  }
}
