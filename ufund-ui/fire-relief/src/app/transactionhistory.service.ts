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
    public transactionHistory: Transaction[] = [];

    constructor(private userService: UserService) {
        this.transactionHistory = this.userService.user.transactionHistory || [];
    }

    addTransaction(transaction: Transaction): void {
        this.transactionHistory.push(transaction);
        this.userService.user.transactionHistory = [...this.transactionHistory];
        this.userService.updateUser()
    }

    getTransactionHistory(): Observable<Transaction[]> {
        return of(this.userService.user.transactionHistory || []);
    }
}
    