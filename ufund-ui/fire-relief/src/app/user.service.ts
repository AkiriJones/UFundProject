import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "./user";
import { catchError, Observable, of } from "rxjs";
import { Basket } from "./basket";

@Injectable({ providedIn: 'root'})

export class UserService {
    private apiUrl = 'http://localhost:8080/users';
    private user!: User; 

    /**
     * header establishes content type as application/json.
     */
    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json'})
    };

    constructor(private http: HttpClient) {}

    /**
     * Gets a list of users from the system
     * 
     * @returns Observable array of user objects
     */
    public getUsers(): Observable<User[]> {
        return this.http.get<User[]>(this.apiUrl).pipe(catchError(this.handleError<User[]>('getUsers', [])));
    }

    /**
     * Finds a specific user matching a username
     * 
     * @param username name of specific user in the system
     * @returns Observable user if found or null if the user does not exist
     */
    public getUser(username: string): Observable<User | null> {
        if (!username.trim()) return of(null); //prevent invalid requests

        return this.http.get<User>(`${this.apiUrl}/${username}`).pipe(
            catchError(error => {
                if (error.status === 404) {
                    console.warn(`User '${username}' not found.`);
                    return of(null); //return null if user is not found
                }
                return this.handleError<User>('getUser', undefined)(error);
            })
        );
    }

    /**
     * Gets the basket of a user in the system
     * 
     * @returns basket of user or empty basket
     */
    public getUserBasket() {
        return this.user?.basket?.items || [];
    }

    /**
     * Adds a user with the given username
     * 
     * @param username specific username
     * @returns Observable new user object
     */
    public addUser(username: string): Observable<User> {
        if (!username.trim()) return of(null as any); //prevent invalid usernames

        const newUser: User = { name: username, basket: new Basket() };
        return this.http.post<User>(this.apiUrl, newUser, this.httpOptions).pipe(
            catchError(this.handleError<User>('addUser'))
        );
    }

    /**
     * Stores user in the user service so it's data can be accessed later
     * 
     * @param user object containing user's data 
     */
    public setCurrentUser(user: User) {
        this.user = user;
    }

    /**
     * Updates a user in the system
     * 
     * @returns Observable user being updated
     */
    public updateUser(): Observable<any> {
        const username = localStorage.getItem("username") || "";
        if (!username) {
            console.error("No username found in localStorage.");
            return of(null);
        }

        const updatedUser: User = { name: username, basket: this.user.basket };
        return this.http.put(`${this.apiUrl}`, updatedUser, this.httpOptions).pipe(
            catchError(this.handleError<any>("updateUser"))
        );
    }

    /**
     * Used for error handling for the above methods
     * 
     * @param operation what was being done that caused the error
     * @param result observable optional value
     * @returns observable with safe result
     */
    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
            console.error(`Error in ${operation}:`, error);
            return of(result as T);
        };
    }
}
