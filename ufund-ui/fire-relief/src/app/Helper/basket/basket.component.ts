import { Component, OnInit } from '@angular/core';
import { Need } from '../../need';
import { BasketService } from '../../basket.service';
import { CupboardService } from '../../cupboard.service';
import { UserService } from '../../user.service';
import { Router } from '@angular/router';
import { Console } from 'node:console';
import { Transaction } from '../../transaction';
import { TransactionHistoryService } from '../../transactionhistory.service';

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
   * @param transactionHistoryService Service for managing transaction data.
   * @param router Servuce to handle navigation.
   */
  constructor(
    private basketService: BasketService,
    private cupboardService: CupboardService,
    private userService: UserService,
    private transactionHistoryService: TransactionHistoryService,
    private router: Router
  ) {}

  /**
   * Lifecycle hook called when the component is initialized.
   * Retrieves current user data based on username.
   */
  ngOnInit(): void {
    const username = localStorage.getItem("username");
    console.log(username);
    if(username) {
      this.userService.getUser(username).subscribe(user => {
        this.userService.user = user;
        console.log("USER" + this.userService.user);
      this.cupboardService.getNeedsFromBasket().subscribe(needs => {
        this.basketItems = needs;
        console.log("BASKET!!!" + this.basketItems);
        this.calculateTotalCost();
      });
      });

      this.cupboardService.getCupboard().subscribe(needs => {
        console.log('All Needs:', needs);
        this.allNeeds = needs;
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
      if( need.quantity > 0){
            console.log("Nothing wrong with the quantity..")
            this.basketService.addToBasket(need);
            this.basketItems.push({need, quantity: 1});
            console.log(this.needs);
            console.log(this.allNeeds);
            this.calculateTotalCost();
      }
      else{
        console.log("Not enough stock.")
      }
  }
  else{
    if(existingItem.quantity+1 <= need.quantity){ //Can only add up the desired need quantity
      existingItem.quantity++;
      this.basketService.updateQuantity(need, existingItem.quantity); //fixed bug with quantity not being updated in backend.
      this.calculateTotalCost();
    }
    else{
      console.log("Cannot add more than stock available.");
      
    }
  }
  

  }
  /**
   * Checks out the current Helper's basket
   */
  checkout(): void {
    
    /* This code breaks the checkout functionality - needs fixing!!!
    
    const newTransaction: Transaction = {
      id: new Date().getTime(),
      needs: this.basketItems.map(item => ({
        id: item.need.id,
        name: item.need.name,
        cost: item.need.cost,
        quantity: item.quantity,
        type: item.need.type
      })),
      total: this.totalCost,
      date: new Date().toISOString().split('T')[0]
    };
  
    this.userService.user.tHistory.push(newTransaction);
    this.transactionHistoryService.addTransaction(newTransaction);
    */ 
    this.basketItems.forEach(element => {
      const updatedNeed: Need = {
        id: element.need.id,
        name: element.need.name,
        cost: element.need.cost,
        quantity: element.need.quantity - element.quantity,
        type: element.need.type
      };
      this.cupboardService.updateNeed(element.need.id, updatedNeed).subscribe();
    });
  
    this.basketService.clearBasket();
    this.basketItems = [];
    this.totalCost = 0;
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
        this.calculateTotalCost()  
      }
      else{
        existingItem.quantity--;
        this.basketService.updateQuantity(need, existingItem.quantity);
        this.calculateTotalCost()
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
   * Navigates to transaction history page
   */
  transactionsButton(): void {
    this.router.navigate(['/transactionhistory']);
  }
}
