import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee-entity/employee-entity';
import { EmployeeService } from '../employee-service/employee-service.service';

@Component({
	selector: 'app-employee-list',
	templateUrl: './employee-list.component.html',
	styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent {
	employees?: Employee[];

	constructor(private employeeService: EmployeeService) { }

	getList() {
		this.employeeService.getAllEmployees().subscribe(data => {
			this.employees = data;
			console.log("employee-list: ", data);
		});
	}
}