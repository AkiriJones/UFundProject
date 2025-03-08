import { Basket } from "./basket"

export class User {
    name : string
    basket : Basket

    public constructor(username : string) {
        this.name = username;
        this.basket = new Basket()
    }
}