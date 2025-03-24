import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "./user";
import { catchError, Observable, of } from "rxjs";
import { Basket } from "./basket";

@Injectable({ providedIn: 'root'})

export class UserService {
    user!: User;

    /**
     * Header establishes content type as application/json.
     */
    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json'})
    };

    constructor(private http: HttpClient) {}

    /**
     * Gets a list of users from the system using HTTP GET request
     * 
     * @returns Observable that emits an array of User objects or empty array if error occurs
     */
    public getUsers(): Observable<User[]> {
        return this.http.get<User[]>('http://localhost:8080/users').pipe(catchError(this.handleError<User[]>('getUsers',[])))
    }

    /**
     * Gets a specific User object that corresponds to given username using HTTP GET request
     * 
     * @param username Name used to access specific user
     * @returns Observable that emits a User object 
     */
    public getUser(username: string) : Observable<User> {
        return this.http.get<User>('http://localhost:8080/users/' + localStorage.getItem("username")).pipe(catchError(this.handleError<any>("getUser" + username)))
    }

    /**
     * Adds a new user to the system usin HTTP POST request
     * 
     * @param username Name used to create a new user
     * @returns Observable that emits the results of user creation
     */
    public addUser(username: string) {
        var basket = new Basket()
        var name = username
        const newUser : User = {name, basket} as User

        return this.http.post<User[]>('http://localhost:8080/users', newUser, this.httpOptions)
    }

    /**
     * Updates user data by retrieving username from localstorage and sending
     * HTTP PUT request to update the user data
     * 
     * @returns Observable that emits the server response or error
     */
    public updateUser(): Observable<User> {
        var name = localStorage.getItem("username");
        console.log(name);
        var basket = this.user.basket;
        console.log(basket);
        const anotherNewUser : User = {name, basket} as User;
        console.log(anotherNewUser);
        return this.http.put<User>('http://localhost:8080/users', anotherNewUser, this.httpOptions).pipe(catchError(this.handleError<any>("Returning User")))
    }

    /**
     * Sets current user
     * 
     * @param user The user object to be set as current user
     */
    public setCurrentUser(user: User) {
        this.user = user;
    }

    /**
     * Retrieves items from current user basket
     * 
     * @returns Items in the user's basket
     */
    public getUserBasket() {
        return this.user.basket.items
    }

    /**
     * Handles HTTP request errors
     * 
     * @param operation The name of the failed operation
     * @param result Optional default value to return in case of error
     * @returns Observable function logs error and returns fallback value
     */
    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
            console.error(error);

            if(operation.includes("getUser")) {
                this.addUser(operation.slice(7)).subscribe(e => e)
            }

            return of(result as T);
        }
    }

}
