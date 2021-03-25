package com.example.demo.order;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.employee.Employee;

// Javax annotation
@Entity @Table(name = "CUSTOMER_ORDER")
public class Order {

	private @Id @GeneratedValue Long id;

	private String description;
	private Status status;
	
	@ManyToOne
	private Employee employee;

	Order() {}
	
	// Used only to preload
	Order(String description, Status status, Employee employee) {
		this.description = description;
		this.status = status;
		this.employee = employee;
	}

	public Long getId() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Order))
			return false;
		Order order = (Order) o;
		return Objects.equals(this.id, order.id) && Objects.equals(this.description, order.description) && this.status == order.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.description, this.status);
	}

	@Override
	public String toString() {
		return "Order{" + "id=" + this.id + ", description='" + this.description + '\'' + ", status=" + this.status + ", EmployeeId=" + this.employee.getId() + '}';
	}
}