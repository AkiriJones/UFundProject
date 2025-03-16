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
  totalCost: number = 0
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
        this.calculateTotalCost();
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

  searchNeeds(): Need[] {
    if(!this.searchTerm) {
      return this.needs;
    }

    return this.needs.filter(need =>
      need.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  addToBasket(need: Need): void {
    const existingItem = this.basketItems.find(item => item.need.id === need.id);
    if (!existingItem){
          this.basketService.addToBasket(need);
          this.basketItems.push({need, quantity: 1});
          this.calculateTotalCost();
    }
    else{
      existingItem.quantity++;
      this.calculateTotalCost();
    }
  }
  /**
   * Checks out the current Helper's basket
   */
  Checkout(): void{
    this.basketItems.forEach(element =>{
      const needContents : Need = {
        id: element.need.id,
        name: element.need.name,
        cost: element.need.cost,
        quantity: element.need.quantity - element.quantity, 
        type: element.need.type}
        this.cupboardService.updateNeed(element.need.id, needContents).subscribe();
    });
    this.basketService.clearBasket();
    this.calculateTotalCost();
    this.basketItems = [];
  }
  /**
   * Calculates the current total cost of the Helper's Basket.
   */
  calculateTotalCost(): void {
    this.totalCost = this.basketItems.reduce((sum, item) => sum + (item.need.cost * item.quantity), 0);
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
