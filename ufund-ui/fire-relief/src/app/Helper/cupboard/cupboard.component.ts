import { Component } from '@angular/core';
import { Need } from '../../need';
import { CupboardService } from '../../cupboard.service';
import { UserService } from '../../user.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

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

  /**
   * Constructs the CupboardComponent.
   * 
   * @param cupboardService Service for retrieving cupboard data.
   * @param userService Service for retrieving user data.
   * @param router Service for navigation within the application.
   */
  constructor(
    private cupboardService: CupboardService,
    //private basketService: BasketService,
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
    if(this.needs) {
      this.observerHandler.unsubscribe();
    }
  }
 
}
