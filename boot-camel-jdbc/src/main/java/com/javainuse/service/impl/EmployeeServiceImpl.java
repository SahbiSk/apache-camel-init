package com.javainuse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.javainuse.model.Employee;

@Service
public class EmployeeServiceImpl extends RouteBuilder {

	@Autowired
	DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void configure() throws Exception {
		//select route
		from("direct:select").process(new Processor() {
			
			@Override
			public void process(Exchange xchg) throws Exception {
				// TODO Auto-generated method stub
				String id=xchg.getIn().getBody(String.class);
				String query=id==null?"select * from employee":"select * from employee where empId ='"+id+"'";
				xchg.getIn().setBody(query);
					}
		}).to("jdbc:dataSource")
				.process(new Processor() {
					public void process(Exchange xchg) throws Exception {
						
						ArrayList<Map<String, String>> dataList = (ArrayList<Map<String, String>>) xchg.getIn()
								.getBody();
						List<Employee> employees = new ArrayList<Employee>();

						System.out.println(dataList);
						for (Map<String, String> data : dataList) {

							Employee employee = new Employee();

							employee.setEmpId(data.get("empId"));
							employee.setEmpName(data.get("empName"));
							employee.setGender(data.get("gender"));
							employee.setDdn(data.get("ddn"));

							employees.add(employee);
						}
						xchg.getIn().setBody(employees);
			}
				});

		
		//insert route
		from("direct:insert").process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				Employee employee = xchg.getIn().getBody(Employee.class);
				String query = "INSERT INTO employee(empId,empName,gender,ddn)values('" + employee.getEmpId() + "','"
						+ employee.getEmpName() + "','" + employee.getGender() + "','" + employee.getDdn()+"')";
				xchg.getIn().setBody(query);
			}
		}).to("jdbc:dataSource");

		//delete
		from("direct:delete").process(new Processor() {
			
			@Override
			public void process(Exchange xchg) throws Exception {
				
				String empId = xchg.getIn().getBody(String.class);
				
				String query= empId!=null?"Delete from employee where empId='"+empId+"'":"Delete from employee";
				 xchg.getIn().setBody(query);
			}
		}).to("jdbc:dataSource");
		
		//update route
		
			from("direct:update").process(new Processor() {
			
			@Override
			public void process(Exchange xchg) throws Exception {
				// TODO Auto-generated method stub
				Employee emp =xchg.getIn().getBody(Employee.class);
				String query= "update employee set "+ ( emp.getEmpName()==null? "":"empName='"+emp.getEmpName() 
				+"' , ")+( emp.getDdn()==null? "":"ddn='"+emp.getDdn() +"' , ")
						+( emp.getGender()==null? "":"gender='"+emp.getGender() 
						+"' , ")   + "empId = '" + emp.getEmpId() + "'" +" where empId='"+emp.getEmpId()+"'";
				System.out.println(query);
						
				xchg.getIn().setBody(query);
				
				
			}
		}).to("jdbc:dataSource");
		
	}
}
