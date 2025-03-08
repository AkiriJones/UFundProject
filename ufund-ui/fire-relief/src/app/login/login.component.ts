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
   * When the login button is clicked, this method uses the input username to navigate to different displays for manager vs. helpers
   */
  onSubmit(): void { 
    if(this.username=="") {
      return;
    }
    localStorage.setItem("username", this.username) //saves user data in browser using key value pair
    this.userService.getUser(this.username).subscribe(user => {
      if(user==undefined) { //if username doesn't exist in the system yet
        this.userService.addUser(this.username).subscribe(user => { //add new user with that username
          this.userService.getUser(this.username).subscribe(user => { //accesses user data
            this.userService.user = user
            console.log(user);
          if(this.username === 'admin') {
            this.router.navigate(['/manager-cupboard']); //if username is admin, redirects to manager display
          }
          else {
            this.router.navigate(['/cupboard']); //naviagtes to helper display
          }
          })
        });
      } else {
        this.userService.user = user
        console.log(user);
        if(this.username === 'admin') {
          this.router.navigate(['/manager-cupboard']);
        }
        else {
          this.router.navigate(['/cupboard']);
        }
      }
  })
}
}
