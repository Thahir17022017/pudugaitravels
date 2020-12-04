package com.pudugaitravels.pojo;

public class Employee {

	
	private String empName;
	private String empStatus;
	
	
	
	
	
	
	
	
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}









	public String getEmpName() {
		return empName;
	}









	public void setEmpName(String empName) {
		this.empName = empName;
	}









	public String getEmpStatus() {
		return empStatus;
	}









	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}









	public Employee(String empName, String empStatus) {
		super();
		this.empName = empName;
		this.empStatus = empStatus;
	}









	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empName == null) ? 0 : empName.hashCode());
		result = prime * result + ((empStatus == null) ? 0 : empStatus.hashCode());
		return result;
	}









	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (empName == null) {
			if (other.empName != null)
				return false;
		} else if (!empName.equals(other.empName))
			return false;
		if (empStatus == null) {
			if (other.empStatus != null)
				return false;
		} else if (!empStatus.equals(other.empStatus))
			return false;
		return true;
	}









	@Override
	public String toString() {
		return "Employee [empName=" + empName + ", empStatus=" + empStatus + "]";
	}
	
	
}
