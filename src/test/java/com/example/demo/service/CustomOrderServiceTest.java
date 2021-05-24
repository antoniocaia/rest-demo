package com.example.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.CustomOrder;
import com.example.demo.model.Employee;
import com.example.demo.model.Status;
import com.example.demo.repository.CustomOrderRepository;

@SpringBootTest
public class CustomOrderServiceTest {

	// I'm trying to avoid using spring in unit test because according some articles it impact test time too much.
	// Class to test
	CustomOrderService orderService;
	// Dependencies to mock
	CustomOrderRepository db;

	@Before
	public void setup() {
		db = mock(CustomOrderRepository.class);
		orderService = new CustomOrderService(db);
	}

	// findById
	@Test
	public void testFindByIdEmptyDbShouldBeEmpty() {
		Optional<CustomOrder> optOrder = orderService.findById(0L);
		assertTrue(optOrder.isEmpty());
	}

	@Test
	public void testFindByIdCorrectId() {
		CustomOrder testOrder = new CustomOrder("test", Status.IN_PROGRESS, new Employee());
		when(db.findById(0L)).thenReturn(Optional.of(testOrder));
		Optional<CustomOrder> optOrder = orderService.findById(0L);
		optOrder.ifPresent(x -> assertEquals(testOrder, x));
	}

	@Test
	public void testFindByIdWrongIdShouldBeEmpty() {
		CustomOrder testOrder = new CustomOrder("test", Status.IN_PROGRESS, new Employee());
		when(db.findById(0L)).thenReturn(Optional.of(testOrder));
		Optional<CustomOrder> optOrder = orderService.findById(1L);
		assertTrue(optOrder.isEmpty());
	}

	// other test

	// save
	@Test
	public void testSaveOrder() {
		CustomOrder testOrder = new CustomOrder("test", Status.IN_PROGRESS, new Employee());
		when(db.save(testOrder)).thenReturn(testOrder);
		assertEquals(testOrder, orderService.save(testOrder));
	}

	// deleteById
	@Test
	public void testDeleteById() {
		orderService.deleteById(0L);
		verify(db).deleteById(0L);
	}

	// delete
	@Test
	public void testDelete() {
		CustomOrder testOrder = new CustomOrder();
		orderService.delete(testOrder);
		verify(db).delete(testOrder);
	}
}
