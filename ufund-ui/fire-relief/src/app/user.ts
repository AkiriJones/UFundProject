import { Basket } from "./basket"
import { Transaction } from "./transaction";

export class User {
    name : string
    basket : Basket
    tHistory : Transaction[];

    public constructor(username : string) {
        this.name = username;
        this.basket = new Basket()
        this.tHistory = [];
    }
}