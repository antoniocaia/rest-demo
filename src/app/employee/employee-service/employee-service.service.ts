import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Employee } from '../employee-entity/employee-entity';
import { Order } from '../../order/order-entity/order-entity';

@Injectable({
	providedIn: 'root'
})
export class EmployeeService {

	employeesUrl = 'http://localhost:8080/employees/';

	constructor(private http: HttpClient) { }

	getAllEmployees(): Observable<Employee[]> {
		return this.http.get<EmployeeResponse>(this.employeesUrl).pipe(
			map(r => r._embedded.employeeList)
		);
	}

	getEmployeeById(id: string): Observable<Employee> {
		return this.http.get<Employee>(this.employeesUrl + id);
	}

	getEmployeeOrders(id: string): Observable<Order[]> {
		return this.http.get<OrderResponse>(this.employeesUrl + id + "/orders").pipe(
			map(r => r._embedded.orderList)
		);
	}

	getEmployeeOrdersById(idEmp: string, idOrd: string): Observable<Order[]> {
		return this.http.get<Order[]>(this.employeesUrl + idEmp + "/orders/" + idOrd);
	}
}

interface EmployeeResponse {
	_embedded: {
		employeeList: Employee[];
		_links: { self: { href: string } };
	};
}

interface OrderResponse {
	_embedded: {
		orderList: Order[];
		_links: { self: { href: string } };
	};
}