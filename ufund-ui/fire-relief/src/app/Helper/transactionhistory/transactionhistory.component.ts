import { Component, OnInit } from '@angular/core';
import { Need } from '../../need';
import { CupboardService } from '../../cupboard.service';
import { UserService } from '../../user.service';
import { Router } from '@angular/router';
import { Transaction } from '../../transaction';
import { TransactionHistoryService } from '../../transactionhistory.service';

/**
 * Component responsible for managing and displaying the user's basket.
 */
@Component({
  selector: 'app-basket',
  standalone: false,
  templateUrl: './transactionhistory.component.html',
  styleUrl: './transactionhistory.component.css'
})
export class TransactionHistoryComponent implements OnInit {
  transactionHistoryItems: { id: number, needs: Array<Need>,  total: number, date: string }[] = []
  
  allTransactions: Transaction[] = [];
  searchTerm: string = '';
  /**
   * Constructs the BasketComponent.
   * 
   * @param transactionHistoryService Service for managing transaction history operations.
   * @param userService Service for managing user data.
   * @param router Servuce to handle navigation.
   */
  constructor(
    private transactionHistoryService: TransactionHistoryService,
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

      this.userService.getUserTransactionHistory().subscribe(transactionHistory => {
        this.transactionHistoryItems = transactionHistory;
        this.calculateTotalCost();
      });
      });

      this.transactionHistoryService.getTransactions().subscribe(transactionHistory => {
        console.log('All Transactions:', transactionHistory);
        this.allTransactions = transactionHistory;
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
      return this.allNeeds;
    }

    return this.needs.filter(need =>
      need.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  /**
   * Adds a need to the basket for checkout if it exist, otherwise increases it's quantity.
   * @param need Need to be added or increased
   */
  addToBasket(need: Need): void {
    const existingItem = this.basketItems.find(item => item.need.id === need.id);
    if (!existingItem){
          this.basketService.addToBasket(need);
          this.basketItems.push({need, quantity: 1});
          this.calculateTotalCost();
    }
    else{
      if(existingItem.quantity < need.quantity){ //Can only add up the desired need quantity
        existingItem.quantity++;
        this.calculateTotalCost();
      }
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
   * Lowers quantity or removes the need from the basket
   * @param need Need to remove or lower quantity of from the basket
   */
  removeFromBasket(need: Need): void{
    const existingItem = this.basketItems.find(item => item.need.id === need.id);
    if (existingItem){
      if(existingItem.quantity - 1 == 0){
        this.basketItems = this.basketItems.filter(item => item.need.id !== need.id);
        this.basketService.removeFromBasket(need);    
      }
      else{
        existingItem.quantity--;
      }
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

  /**
     * Shows transaction history.
     */
    transactionHistoryButton(): Transaction[] {
      return this.transactionHistory;
    }
}
