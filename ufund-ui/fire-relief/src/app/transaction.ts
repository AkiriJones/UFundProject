import { Need } from "./need";

export interface Transaction {
    id: number;
    needs: Need[];
    total: number;
    date: string;
}