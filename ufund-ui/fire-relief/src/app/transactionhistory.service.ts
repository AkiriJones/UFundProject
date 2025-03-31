import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Transaction } from "./transaction";
import { catchError, forkJoin, map, Observable, of } from "rxjs";
import { Need } from "./need";
import { UserService } from "./user.service";

/**
 * Service to manage the contents of the user's transaction history.
 * Synchronizes the data on the front end with the user's data on the server.
 */
@Injectable({ providedIn: 'root' })

export class TransactionHistoryService {
    public transactionHistory: Transaction[] = []; //transaction history for current user
    
    /**
     * Constructor for the service. 
     * Initializes the service with the user's transaction history from the UserService.
     * @param userService The service responsible for managing user data.
     */
    constructor(private userService: UserService) {
        this.transactionHistory = this.userService.user.tHistory || [];
    }

    /**
     * Adds a new transaction to the user's transaction history.
     * Updates the user's transaction history in the UserService and synchronizes it with the server.
     * @param transaction The transaction to be added to the user's history.
     */
    addTransaction(transaction: Transaction): void {
        this.transactionHistory.push(transaction);
        this.userService.user.tHistory = [...this.transactionHistory];
        this.userService.updateUser().subscribe({
            next: (response) => console.log("User updated successfully:", response),
            error: (err) => console.error("Error updating user:", err)
        });
    }
    
    /**
     * Retrieves the user's transaction history.
     * @returns an observable of the user's transaction history.
     */
    getTransactionHistory(): Observable<Transaction[]> {
        return of(this.userService.user.tHistory || []);
    }
}
    