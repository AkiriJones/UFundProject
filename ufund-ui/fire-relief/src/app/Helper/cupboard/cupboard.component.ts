import { Component } from '@angular/core';
import { Need } from '../../need';
import { CupboardService } from '../../cupboard.service';
import { UserService } from '../../user.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { BasketService } from '../../basket.service';

/**
 * Component responsible for displaying cupboard contents to helpers.
 */
@Component({
  selector: 'app-cupboard',
  standalone: false,
  templateUrl: './cupboard.component.html',
  styleUrl: './cupboard.component.css'
})
export class CupboardComponent {
  needs: Need[] = [];
  observerHandler!: Subscription
  searchTerm: string = '';
  displayCupboard: boolean = true;

  /**
   * Constructs the CupboardComponent.
   * 
   * @param cupboardService Service for retrieving cupboard data.
   * @param userService Service for retrieving user data.
   * @param router Service for navigation within the application.
   */
  constructor(
    private cupboardService: CupboardService,
    private basketService: BasketService,
    private userService: UserService,
    private router: Router
  ) {}

  /**
   * Lifecycle hook that runs when the component initializes.
   * Fetches cupboard data and assigns it to needs array.
   */
  ngOnInit(): void {
    this.cupboardService.getCupboard().subscribe((needs: Need[]) => {
      this.needs = needs;
    })
  }

  /**
   * Lifecycle hook that runs when the component is destroyed.
   * Unsubscribes from active subscriptions to prevent memory leaks.
   */
  ngOnDestroy(): void {
    if(this.observerHandler) {
      this.observerHandler.unsubscribe();
    }
  }

  /**
   * Searches for needs that match a given name, otherwise displays entire cupboard.
   * 
   * @returns Need list that matches the search term.
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
   * Adds a need to the basket
   * 
   * @param need The need object being added to the basket.
   */
  addToBasket(need: Need): void {
    this.basketService.addToBasket(need);
  }

  /**
   * Navigates to basket.
   */
  basketButton(): void {
    this.router.navigate(['/basket']);
    this.displayCupboard = false;
  }

  /**
   * Navigates to cupboard.
   */
  cupboardButton(): void {
    this.router.navigate(['/cupboard']);
    this.displayCupboard = true;
  }

  /**
   * Shows transaction history.
   */
  transactionHistoryButton(): void {
    
  }
}
