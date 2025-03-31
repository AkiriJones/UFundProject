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
  selectedLocation: string = 'All';
  needTypes: string[] = ['All'];
  needLocations: string[] = ['All'];

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
        this.filteredNeeds = [...this.needs];
        this.dynamicNeedTypeList();
        this.dynamicLocationList();
    });
  }

  dynamicLocationList(): void {
    const locationSet = new Set<string>();
    this.needs.forEach(need => {
      if (need.location && need.location.trim() !== '') {
        locationSet.add(need.location.trim());
      }
    });
    this.needLocations = ['All', ...Array.from(locationSet)];
    if (!this.needLocations.includes(this.selectedLocation)) {
      this.selectedLocation = 'All';
    }
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
    if (!this.needTypes.includes(this.selectedType)) {
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
      const matchesLocation = this.selectedLocation === 'All' || need.location === this.selectedLocation;
      const matchesSearch = this.searchTerm === '' || need.name.toLowerCase().includes(this.searchTerm.toLowerCase());

      return matchesType && matchesLocation && matchesSearch;
    });
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
}
