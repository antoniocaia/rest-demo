package com.example.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;	
	@MockBean
	private EmployeeService service;
		
	@Test
	@WithMockUser(username = "user", password = "test", roles = "USER")
	public void getCustomOrdersTest() throws Exception {
		when(service.findById(0L)).thenReturn(Optional.of(new Employee("myName", "myJob", new HashSet<>())));
		
		this.mockMvc.perform(get("/api/v1/employees/{id}/order"))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.Employee.name").value("myName"))
		.andExpect(jsonPath("$.Employee.job").value("myjob"));
	}
}
