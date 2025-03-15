import { Injectable } from "@angular/core";
import { Need } from "./need";
import { UserService } from "./user.service";
import { Basket } from "./basket";
import { CupboardService } from "./cupboard.service";

@Injectable({
    providedIn: 'root'
})
export class BasketService {
    public basket: {need: Need, quantity: number}[] = [];

    constructor(private userService: UserService, private cupboardService: CupboardService) {
        this.cupboardService.getNeedsFromBasket().subscribe(needs => {
            this.basket = needs;
        });
    }
}