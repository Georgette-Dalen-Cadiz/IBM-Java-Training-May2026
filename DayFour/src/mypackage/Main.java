package mypackage;

import java.util.*;

public class Main {
	public static void main(String[] args) 
	{
		
		List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", "IT", 55000));
        employees.add(new Employee("Bob", "Finance", 60000));
        employees.add(new Employee("Alice", "HR", 52000)); // duplicate name
        employees.add(new Employee("Ken", "IT", 60000));
        employees.add(new Employee("Maria", "HR", 50000));
        employees.add(new Employee("John", "Finance", 70000));
        employees.add(new Employee("Ken", "Finance", 65000)); // duplicate name
        employees.add(new Employee("Lara", "IT", 62000));
        employees.add(new Employee("Sam", "HR", 48000));
        employees.add(new Employee("Bob", "IT", 59000)); // duplicate name
		
		
        // SET
		String setEmp = "";
		Set<String> setTemp = new HashSet<>();
		for (Employee e : employees)
		{
			
			boolean exists = setTemp.contains(e.name);
			if (exists)
			{
			
			} else {
				setTemp.add(e.name);
				setEmp = setEmp + e.toString();
			}
		}
		
		System.out.println("=== Unique Employees ===");
		System.out.println(setEmp);
		
		
		
		
		
		// EMPLOYEES BY DEPARTMENT
		System.out.println("\n=== Employees by Department ===");
		Map<String, List<Employee>> mapEmp = new HashMap<>();
		
		for (Employee e : employees) {
		    List<Employee> list = mapEmp.get(e.department);

		    if (list == null) {
		        list = new ArrayList<>();
		        mapEmp.put(e.department, list);
		    }

		    list.add(e);
		}
		
		for (Map.Entry<String, List<Employee>> entry : mapEmp.entrySet()) {
		    String department = entry.getKey();
		    List<Employee> empList = entry.getValue();

		    System.out.println(department);

		    for (Employee e : empList) {
		        System.out.print(e.toString());
		    }
		}
		
		// Highest Paid per Department 
		System.out.print("\n=== Highest Paid per Department ===\n");
		for (Map.Entry<String, List<Employee>> entry : mapEmp.entrySet()) {
		    String department = entry.getKey();
		    List<Employee> empList = entry.getValue();

		    double highestDep = 0;
		    String name = "";
		    String output = "";
		    String currEmp = "";
		    for (Employee e : empList) {
		    	double curr = e.salary;
		        if (curr >= highestDep)
		        {
		        	highestDep = curr;
		        	name = e.name;
		        	currEmp = "";
		        	currEmp += e.toString();
		        }
		    }
		    
		    // output = output + "\n" + department + ": " + name + " | " + highestDep;
		    System.out.print(currEmp);
		}
		
		
		
		// SORT ALL EMPLOYEES BY SALARY (DESCENDING)
		 Comparator c = Collections.reverseOrder(new SortbySalary());
		 Collections.sort(employees, c);
		 
		 System.out.println("\n\n=== Employees Sorted by Salary (Desc) ===");
		 
		 for (Employee e : employees)
		 {
			 System.out.print(e.toString());	 
		 }
		 
		 
	         
		
		// CREATE A SET OF ALL UNIQUE SALARY
		 Set<Double> setSal =  new TreeSet<>();
		
		 for (Employee e : employees)
		 {
			 setSal.add(e.salary);
		 }
		 System.out.println("\n=== Unique Salaries (Sorted) ===");
		for (double i : setSal)
		{
			System.out.println(i);
		}
	}
}


