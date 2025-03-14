import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, of } from "rxjs";
import { Need } from "./need";

/**
 * Service to manage the contents of the cupboard.
 */
@Injectable({ providedIn: 'root'})
export class CupboardService {

    private cupboardUrl = 'http://localhost:8080/needs';

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    /**
     * Constructs the service and injects HttpClient dependency.
     * 
     * @param http HttpClient service for making HTTP requests.
     */
    constructor(private http: HttpClient) {}

    /**
     * Fetches the entire list of needs from the cupboard.
     * 
     * @returns Observable containing an array of Needs.
     */
    getCupboard(): Observable<Need[]> {
        return this.http.get<Need[]>(this.cupboardUrl).pipe(catchError(this.handleError<Need[]>('getCupboard', [])));
    }

    /**
     * Fetches a specific need by its ID.
     * 
     * @param id The ID of the need to retrieve.
     * @returns An Observable containing the requested Need object.
     */
    getNeed(id: number): Observable<Need> {
        const url = `${this.cupboardUrl}/?id=${id}`;
        return this.http.get<Need>(url).pipe(catchError(this.handleError<Need>(`getNeed id=${id}`)));
    }

    /**
     * Adds a new need to the cupboard.
     * 
     * @param need The Need object to be added.
     * @returns An Observable containing the new Need object added.
     */
    addNeed(need: Need): Observable<Need> {
        return this.http.post<Need>(this.cupboardUrl, need, this.httpOptions).pipe(catchError(this.handleError<Need>('addNeed')));
    }

    /**
     * Deletes a need from the cupboard.
     * 
     * @param id The ID of the Need object that is being deleted.
     * @returns An Observable indicating operation result.
     */
    deleteNeed(id: number): Observable<void> {
        const url =  `${this.cupboardUrl}/${id}`;
        return this.http.delete<void>(url, this.httpOptions).pipe(catchError(this.handleError<void>('deleteNeed')));
    }

    /**
     * Updates an existing need in the cupboard.
     * 
     * @param id The id of the need being updated.
     * @param need The need object with updated values.
     * @returns An Observable containing the updated Need object.
     */
    updateNeed(id: number, need: Need): Observable<Need> {
        const url = `${this.cupboardUrl}/${id}`;
        return this.http.put<Need>(url, need, this.httpOptions).pipe(catchError(this.handleError<Need>('UpdateNeed')));
    }

    /**
     * Handles failed http operations, lets the app continue to run.
     * 
     * @param operation The name of the operation that failed.
     * @param result the optional default value to return.
     * @returns A function that handles the error and returns an Observable with a default value.
     */
    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
            console.error(error);

            return of(result as T);
        }
    }
}