import { Component, OnInit } from '@angular/core';
import { Need } from '../../need';
import { CupboardService } from '../../cupboard.service';
import { UserService } from '../../user.service';
import { Router } from '@angular/router';

/**
 * Component for managing the cupboard view for managers.
 */
@Component({
  selector: 'app-manager-cupboard',
  standalone: false,
  templateUrl: './manager-cupboard.component.html',
  styleUrl: './manager-cupboard.component.css'
})

export class ManagerCupboardComponent implements OnInit {
  cupboard: Need[] = [];
  isEmpty: boolean = false;
  selectedNeed: Need | null = null;

  /**
   * Constructs the ManagerCupboardComponent.
   * 
   * @param cupboardService Service for managing cupboard data.
   * @param userService Service for managing user data.
   * @param router router service for navigation.
   */
  constructor(
    private cupboardService: CupboardService,
    private userService: UserService,
    private router: Router
  ) {}

  /**
   * Lifecycle hook that runs when the component is initialized.
   * Calls getCupboard() to fetch cupboard data.
   */
  ngOnInit(): void {
    this.getCupboard();
  }

  /**
   * Fetches the cupboard data from the CupboardService and updates the cupboard array.
   * If the cupboard is empty, isEmpty turns to true.
   */
  getCupboard(): void {
    this.cupboardService.getCupboard().subscribe(cupboard => {
      this.cupboard = cupboard;
      this.isEmpty = cupboard.length === 0; 
    });
  }
  
  /**
   * Adds a need to the cupboard by calling HTTP request POST method from CupboardService.
   * Ensures that when a need is added, isEmpty turns to false.
   * 
   * @param name name of the need to be added.
   * @param cost cost of the need to be added.
   * @param quantity quantity of the need to be added.
   * @param type type of the need to be added.
   * @param location location of the need
   */
  addNeed(name: string, cost: number, quantity: number, type: string, location: string): void {
    name = name.trim();
    type = type.trim();
    location = location.trim();
    if(!name || !type || cost <= 0 || quantity <= 0 || !location) {
      return;
    }

    const newNeed: Need = {
      id: 0,
      name: name,
      cost: cost,
      quantity: quantity,
      type: type,
      location: location
    };

    this.cupboardService.addNeed(newNeed).subscribe((addedNeed) => {
      this.cupboard.push(addedNeed);
      this.isEmpty = false;
    }) 
  }

  /**
   * Deletes a need from the cupboard.
   * 
   * @param id The ID of the need to delete.
   */
  deleteNeed(id: number): void {
    this.cupboardService.deleteNeed(id).subscribe(() => {
      this.cupboard = this.cupboard.filter(need => need.id !== id);
      this.isEmpty = this.cupboard.length === 0;
    });
  }

  /**
   * Selects need for editing.
   * 
   * @param need The need object to edit.
   */
  editNeed(need: Need): void {
    this.selectedNeed = { ...need };
  }

  /**
   * Saves the updated need to the cupboard.
   */
  saveNeed(): void {
    if(this.selectedNeed) {
      this.cupboardService.updateNeed(this.selectedNeed.id, this.selectedNeed).subscribe(updatedNeed => {
        const index = this.cupboard.findIndex(n => n.id === updatedNeed.id);
        if(index !== -1) {
          this.cupboard[index] = updatedNeed;
        }
        this.selectedNeed = null;
      });
    }
  }

  /**
   * Cancels the edit.
   */
  cancelEdit(): void {
    this.selectedNeed = null;
  }
  
  logoutButton(): void {
    this.router.navigate(['/login']);
  }

}
