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
  allNeeds: Need[] = [];
  basketItems: { need: Need, quantity: number }[] = []
  totalCost: number = 0
  observerHandler!: Subscription
  searchTerm: string = '';
  displayCupboard: boolean = true;
  filteredNeeds: Need[] = [];
  selectedType: string = 'All';
  needTypes: string[] = ['All'];

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
      this.dynamicNeedTypeList();
      this.filterNeeds();
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
   * Generates a dynamic list of unique need types based on the existing needs.
   * If no valid types are found, only "All" is retained.
   * Ensures `selectedType` remains valid after updating the list.
   * 
   * @returns {void}
   */
  dynamicNeedTypeList(): void {
    const typeSet = new Set<string>();

    this.needs.forEach(need => {
      if (need.type && need.type.trim() !== '') {
        typeSet.add(need.type.trim());
      }
    });

    this.needTypes = ['All', ...Array.from(typeSet)];

    if (this.needTypes.length === 1) {
      this.needTypes = ['All'];
    }

    if (this.selectedType !== 'All' && !this.needTypes.includes(this.selectedType)) {
      this.selectedType = 'All';
    }
  }
  
  /**
   * Filters the list of needs based on the selected type and search term.
   * If  the selectedType is "All", all needs are included.
   * If a searchTerm is provided, filters needs whose names contain the type.
   * 
   * @returns {void}
   */
filterNeeds(): void {
    this.filteredNeeds = this.needs.filter(need => {
      const matchesType = this.selectedType === 'All' || need.type === this.selectedType;
      const matchesSearch = this.searchTerm === '' || need.name.toLowerCase().includes(this.searchTerm.toLowerCase());
      
      return matchesType && matchesSearch;
    });
  }

  /**
   * Adds a need to the basket
   * 
   * @param need The need object being added to the basket.
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
   * Calculates the current total cost of the Helper's Basket.
   */
    calculateTotalCost(): void {
      this.totalCost = this.basketItems.reduce((sum, item) => sum + (item.need.cost * item.quantity), 0);
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

  logoutButton(): void {
    this.router.navigate(['/login']);
  }
}
