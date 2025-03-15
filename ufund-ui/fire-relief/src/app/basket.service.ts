import { Injectable } from "@angular/core";
import { Need } from "./need";
import { UserService } from "./user.service";
import { CupboardService } from "./cupboard.service";

/**
 * Service to manage the contents of the user's basket.
 * Synchronizes the basket data with the user's data on the server.
 */
@Injectable({
    providedIn: 'root'
})
export class BasketService {
    public basket: {need: Need, quantity: number}[] = [];

    /**
     * Constructs the BasketService and initializes the basket.
     * 
     * @param userService Service to manage user data.
     * @param cupboardService Service to manage cupboard data.
     */
    constructor(private userService: UserService, private cupboardService: CupboardService) {
        this.cupboardService.getNeedsFromBasket().subscribe(needs => {
            this.basket = needs;
        });
    }

    /**
     * Updates the user's basket by syncing the local basket array with the user's data stored on the server.
     */
    updateBasket() {
        var newBasket : number[][] = []
        for(let i = 0; i < this.basket.length; i++) {
            newBasket.push([this.basket[i].need.id, this.basket[i].quantity])
        }
        this.userService.user.basket.items = newBasket
        this.userService.updateUser();
    }

    /**
     * Adds a need to the basket. 
     * If the need exists, it increments quantity by 1.
     * Otherwise, it adds the need to the basket with a quantity of 1.
     * 
     * @param needAdded The need to add to the basket
     */
    addToBasket(needAdded: Need): void {
        if(this.basket.length == 0) {
            this.basket.push({need : needAdded, quantity: 1})
            this.updateBasket()
            return
        }

        var index: number = 0
        var inBasket : boolean = false

        this.basket.forEach(value => {
            if(value["need"].id == needAdded.id) {
                this.basket[index]["quantity"]++
                inBasket = true;
            } 
            else {
                index++
            }
        })

        if(!inBasket) {
            this.basket.push({need: needAdded, quantity: 1})
        }

        this.updateBasket()
    }

    /**
     * Removes a need from the basket.
     * If the need is found, it removes the need and updates the basket.
     * If the basket only contains one item, it pops the item.
     * Otherwise, removes the need at the specific index.
     * 
     * @param need The need to remove from the basket.
     */
    removeFromBasket(need: Need): void {
        for(var i = 0; i<this.basket.length; i++) {
            if(this.basket[i].need.id == need.id) {
                if(this.basket.length == 1) {
                    this.basket.pop()
                    break
                }
                else {
                    this.basket.splice(i, 1)
                    break
                }
            }
        }
        this.updateBasket()
    }

    /**
     * Clears all items in the basket by setting the basket to an empty array.
     */
    clearBasket(): void {
        this.basket = []
        this.updateBasket()
    }

    /**
     * Updates the quantity of a specific need in the basket.
     * 
     * @param need The need whose quantity is to be updated.
     * @param quantity The new quantity for the need.
     */
    updateQuantity(need: Need, quantity: number): void {
        for(var i = 0; i < this.basket.length; i++) {
            if(this.basket[i].need.id == need.id) {
                this.basket[i].quantity = quantity;
            }
        }
        this.updateBasket()
    }

    /**
     * Retrieves the current items in the user basket, each item consisting of a need and its quantity.
     *  
     * @returns An array of objects containing the need and its quantity
     */
    getItems(): {need : Need, quantity: number}[] {
        var items: {need : Need, quantity: number}[] = []
        for(let i = 0; i < this.userService.getUserBasket().length; i++) {
            this.cupboardService.getNeed(this.userService.getUserBasket()[i][0]).subscribe(item => {
                items.push({need: item, quantity : this.userService.getUserBasket()[i][1]})
            })
        }
        return items;
    }
}