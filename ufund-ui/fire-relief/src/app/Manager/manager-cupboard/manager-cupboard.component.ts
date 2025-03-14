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
   */
  getCupboard(): void {
    this.cupboardService.getCupboard().subscribe(cupboard => this.cupboard = cupboard);
  }
  
}
