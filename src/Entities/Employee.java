package Entities;

import java.util.Date;

public class Employee {

	private int employeeId;
	private String name;
	private String address;
	private String gender;
    private Date birthDate;
	private double salary;
	
	
	
	public Employee() {
		super();
	}

//	public Employee(String name, String address, String gender, Date birthDate, double salary) {
//
//		this.name = name;
//		this.address = address;
//		this.gender = gender;
//		this.birthDate = birthDate;
//		this.salary = salary;
//	}

	public Employee(int employeeId, String name, String address, String gender, Date birthDate, double salary) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.address = address;
		this.gender = gender;
		this.birthDate = birthDate;
		this.salary = salary;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", address=" + address + ", gender=" + gender + ", birthDate=" + birthDate
				+ ", salary=" + salary + "]";
	}

}