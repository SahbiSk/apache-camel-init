package com.javainuse.controller;
import java.util.ArrayList;
import java.util.List;


import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javainuse.model.Employee;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class EmployeeController {

	@Autowired
	ProducerTemplate producerTemplate;

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	@ResponseBody
	public List<Employee> getAllEmployees() {
		List<Employee> employees = producerTemplate.requestBody("direct:select", null, List.class);
		return employees;
	}
	
	
/*	@RequestMapping(value = "/employees/{empId}", method = RequestMethod.GET)
	@ResponseBody
	public Employee getEmployee(String id) {
		List<Employee> list = getAllEmployees();
		for(Employee x : list) {
			if(x.getEmpId().equals(id)) {
				return x;
			}
		}
		return null;
	}
	*/


	@RequestMapping(value = "/employees", consumes = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<Employee>> insertEmployee(@RequestBody Employee emp) {
		producerTemplate.requestBody("direct:insert", emp, List.class);
		return  new ResponseEntity<>(getAllEmployees(), HttpStatus.CREATED);

	}
	
	@RequestMapping(value = "/employees", consumes = "application/json", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<List<Employee>> deleteEmployee(@RequestBody Employee emp) {
		producerTemplate.requestBody("direct:delete", null, List.class);
		return  new ResponseEntity<>(getAllEmployees(), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/employees/{empId}", consumes = "application/json", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<List<Employee>> deleteEmployeeById(@PathVariable(value = "empId") @RequestBody String empId) {
		producerTemplate.requestBody("direct:delete", empId, String.class);
		return  new ResponseEntity<>(getAllEmployees(), HttpStatus.OK);
	}
	
	/*
	@RequestMapping(value = "/employees/{empId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "empId") @RequestBody String empId) {
		List<Employee> employees = producerTemplate.requestBody("direct:select", empId, List.class);
		return new ResponseEntity<>(getEmployee(empId), HttpStatus.OK);

	}*/
	
	@RequestMapping(value = "/employees", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<List<Employee>> updateEmployeeById(@RequestBody Employee emp) {
		List<Employee> employees = producerTemplate.requestBody("direct:update", emp, List.class);
		return new ResponseEntity<>(getAllEmployees(), HttpStatus.OK);

	}

}
