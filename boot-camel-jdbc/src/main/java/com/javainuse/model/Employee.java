package com.javainuse.model;



public class Employee {

	private String empId;
	private String empName;
	private String gender;
	private String ddn;

	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDdn() {
		return ddn;
	}
	public void setDdn(String ddn) {
		this.ddn = ddn;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", gender=" + gender + ", ddn=" + ddn + "]";
	}

	

}