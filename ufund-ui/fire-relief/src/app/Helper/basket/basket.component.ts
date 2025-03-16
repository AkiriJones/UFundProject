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
  searchTerm: string = '';
  needs: Need[] = [];
  allNeeds: Need[] = [];
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

      this.cupboardService.getCupboard().subscribe(needs => {
        console.log('All Needs:', needs);
        this.allNeeds = needs;
        this.needs = needs;
      });
    }
    else {
      console.log("No username found");
    }
  }
  /**
   * Searches for the needs that can be added to a basket.
   * @returns List of needs corresponding to the search term.
   */
  searchNeeds(): Need[] {
    if(!this.searchTerm) {
      return this.needs;
    }

    return this.needs.filter(need =>
      need.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  /**
   * Adds a need to the basket for checkout.
   * @param need Need to be added
   */
  addToBasket(need: Need): void {
    const existingItem = this.basketItems.find(item => item.need.id === need.id);
    if (!existingItem){
          this.basketService.addToBasket(need);
          this.basketItems.push({need, quantity: 1});
    }
    else{
      existingItem.quantity++;
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
