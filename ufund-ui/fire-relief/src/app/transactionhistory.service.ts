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
@Injectable({
    providedIn: 'root'
})

export class TransactionHistoryService {
    public transactionHistory: {transaction: Transaction}[] = [];

    // constructor(private userService: UserService) {
    //         this.userService.getNeedsFromBasket().subscribe(needs => {
    //             this.basket = needs;
    //         });
}