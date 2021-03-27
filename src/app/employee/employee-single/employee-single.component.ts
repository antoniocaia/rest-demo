import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Order } from '../../order/order-entity/order-entity';
import { Employee } from '../employee-entity/employee-entity';
import { EmployeeService } from '../employee-service/employee-service.service';
import { OrderService } from '../../order/order-service/order-service.service';

@Component({
	selector: 'app-employee-single',
	templateUrl: './employee-single.component.html',
	styleUrls: ['./employee-single.component.css']
})
export class EmployeeSingleComponent {
	employee?: Employee;
	orders?: Order[];

	idF = new FormControl('');

	constructor(private employeeService: EmployeeService, private orderService: OrderService) { }

	getEmployeeById() {
		this.orders = undefined;
		this.employeeService.getEmployeeById(this.idF.value).subscribe(data => {
			this.employee = data;
			console.log("single-employee: ", data);
		});
	}

	getEmployeeOrders() {
		this.employeeService.getEmployeeOrders(this.idF.value).subscribe(data => {
			this.orders = data;
			console.log("single-employee: ", data);
		});
	}

	completeOrder(id: string) {
		console.log("compelte: ", id);
		console.log(this.orderService.completeOrder(id));
	}

	cancelOrder(id: string) {
		console.log("delete: ", id);
		console.log(this.orderService.deleteOrder(id));
	}
}
