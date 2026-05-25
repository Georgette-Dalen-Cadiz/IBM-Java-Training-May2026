package mypackage;

import java.util.Comparator;

class SortbySalary implements Comparator<Employee> {
    
    public int compare(Employee a, Employee b)
    {
        return (int) (a.salary - b.salary);
    }
}