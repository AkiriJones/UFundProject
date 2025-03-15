import { Component, OnInit } from '@angular/core';
import { Need } from '../../need';
import { BasketService } from '../../basket.service';
import { CupboardService } from '../../cupboard.service';
import { UserService } from '../../user.service';
import { Router } from '@angular/router';

/**
 * Component responsible for managing and displaying the user's basket.
 */
@Component({
  selector: 'app-basket',
  standalone: false,
  templateUrl: './basket.component.html',
  styleUrl: './basket.component.css'
})
export class BasketComponent implements OnInit {
  basketItems: { need: Need, quantity: number }[] = []

  /**
   * Constructs the BasketComponent.
   * 
   * @param basketService Service for managing basket operations.
   * @param cupboardService Service for retrieving cupboard data.
   * @param userService Service for managing user data.
   * @param router Servuce to handle navigation.
   */
  constructor(
    private basketService: BasketService,
    private cupboardService: CupboardService,
    private userService: UserService,
    private router: Router
  ) {}

  /**
   * Lifecycle hook called when the component is initialized.
   * Retrieves current user data based on username.
   */
  ngOnInit(): void {
    const username = localStorage.getItem("username");

    if(username) {
      this.userService.getUser(username).subscribe(user => {
        this.userService.user = user;

      this.cupboardService.getNeedsFromBasket().subscribe(needs => {
        this.basketItems = needs;
      });
      });
    }
    else {
      console.log("No username found");
    }
  }

  /**
   * Navigates to cupboard
   */
  cupboardButton(): void {
    this.router.navigate(['/cupboard']);
  }

  /**
   * Navigates to basket
   */
  basketButton(): void {
    this.router.navigate(['/basket']);
  }
}
