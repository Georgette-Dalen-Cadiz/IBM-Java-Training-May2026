package mypackage;

public class Employee {

	String name, department;
	double salary;
	
	// CONSTRUCTOR
	Employee(String name, String department, double salary)
	{
		this.name = name;
		this.department = department;
		this.salary = salary;
	}
	
	// GETTERS
	public String getName()
	{
		return name;
	}
	
	public String getDepartment()
	{
		return department;
	}
	
	public double getSalary()
	{
		return salary;
	}
	
	@Override
	public String toString() {
		return name + " | " + department + " | " + salary + "\n";
	}
}
