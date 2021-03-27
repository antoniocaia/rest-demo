import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Injectable({
	providedIn: 'root'
})
export class OrderService {

	ordersUrl = 'http://localhost:8080/orders/';

	constructor(private http: HttpClient) { }

	completeOrder(id: string): any {
		return this.http.put(this.ordersUrl + id + "/complete", "");
	}

	deleteOrder(id: string): any {
		return this.http.delete(this.ordersUrl + id + "/cancel");
	}
}
