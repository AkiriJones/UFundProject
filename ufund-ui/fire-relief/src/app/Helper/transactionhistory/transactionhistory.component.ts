import { Component, OnInit } from '@angular/core';
import { Need } from '../../need';
import { CupboardService } from '../../cupboard.service';
import { UserService } from '../../user.service';
import { Router } from '@angular/router';
import { Transaction } from '../../transaction';
import { TransactionHistoryService } from '../../transactionhistory.service';

/**
 * Component responsible for managing and displaying the user's transaction history.
 */
@Component({
  selector: 'app-basket',
  standalone: false,
  templateUrl: './transactionhistory.component.html',
  styleUrl: './transactionhistory.component.css'
})
export class TransactionHistoryComponent implements OnInit {
  
  allTransactions: Transaction[] = [];

  /**
   * Constructs the TransactionHistory Component.
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
   * Lifecycle hook that is called when the component is initialized.
   * It retrieves the username from localStorage and loads the user's transaction history.
   */
  ngOnInit(): void {
    const username = localStorage.getItem("username");

    if(username) {
      this.userService.getUser(username).subscribe(user => {
        this.userService.user = user;
        this.transactionHistoryService.loadTransactionHistory();
        this.loadTransactionHistory();
      });
    }

    else {
      console.log("No username found");
    }
  }

  /**
   * Loads the user's transaction history by making a request to the transaction history service.
   */
  loadTransactionHistory(): void {
    this.transactionHistoryService.getTransactionHistory().subscribe(transactions => {
      this.allTransactions = transactions;
      console.log("Transaction history loaded successfully:", this.allTransactions);
    });
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
