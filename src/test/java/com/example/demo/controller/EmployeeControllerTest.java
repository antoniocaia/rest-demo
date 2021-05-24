package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.CustomOrder;
import com.example.demo.model.Employee;
import com.example.demo.model.Status;
import com.example.demo.service.EmployeeService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private EmployeeService service;
	
	@Test
	@WithMockUser(username = "user", password = "test", roles = "USER")
	public void getCustomOrdersTest() throws Exception {
		final Long empId = 0L;
		final Long ordId = 0L;

		Set<CustomOrder> orderSet = new HashSet<>();
		CustomOrder customOrder1 = new CustomOrder("myDescription", Status.COMPLETED);
		CustomOrder customOrder2 = new CustomOrder("myDesc", Status.IN_PROGRESS);
		orderSet.add(customOrder1);
		orderSet.add(customOrder2);

		when(service.findById(empId)).thenReturn(Optional.of(new Employee("myName", "myJob", orderSet)));
		this.mockMvc.perform(get("/api/v1/employees/" + empId + "/order/" + ordId)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.description", is(customOrder1.getDescription())))
				.andExpect(jsonPath("$.status", is(customOrder1.getStatus().toString())));
	}
}
