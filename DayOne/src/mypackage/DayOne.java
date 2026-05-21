package mypackage;

import java.lang.*;
import java.util.*;

public class DayOne {
	
	// 1. Task 1 - blackJack Logic
	public static int blackJack(int a, int b)
	{
		
		if(a > 21 && b > 21)
		{
			return 0;
		} 
		else if (a > 21)
		{
			return b;
		} 
		else if (b > 21)
		{
			return a;
		}
		else 
		{
			if (a > b)
			{
				return a;
			}
			else
			{
				return b;
			}
		}
			
		
	}
	
	// 2. Task 2 - Day of the Week using Switch
	public static void dayWeekSwitch()
	{
		int a;
		
		System.out.print("Enter Number: ");
		
		Scanner sc = new Scanner(System.in);
		a = sc.nextInt();
		
		switch(a)
		{
			case 1:
				System.out.println("Monday");
			    break;
			case 2:
				System.out.println("Tuesday");
			    break;
			case 3:
				System.out.println("Wednesday");
			    break;
			case 4:
				System.out.println("Thurday");
			    break;
			case 5:
				System.out.println("Friday");
			    break;
			case 6:
				System.out.println("Saturday");
			    break;
			case 7:
				System.out.println("Sunday");
			    break;
			default:
				System.out.println("Invalid Number");
				break;
		}
		
	
		
	}
	
	// 3. Task 3
	public static void dayWeekPattern()
	{
		
		System.out.print("Enter Number: ");
	    
	    Scanner sc = new Scanner(System.in);
	    int a = sc.nextInt();

	    String result = switch (a) {
	        case 1 -> "Monday";
	        case 2 -> "Tuesday";
	        case 3 -> "Wednesday";
	        case 4 -> "Thursday";
	        case 5 -> "Friday";
	        case 6 -> "Saturday";
	        case 7 -> "Sunday";
	        default -> "Invalid Number";
	    };

	    System.out.println(result); 
	    
	}

	// 4. Task 4
	public static void stairCount()
	{
	    int n = 0;
	    Scanner sc = new Scanner(System.in);
	    boolean isNum = true;
	    
	    while (isNum)
	    {
	        System.out.print("Enter a number: ");
	        n = sc.nextInt();
	        if (n >= 1 && n <= 20)
	        {
	            isNum = false;
	        }
	    }
	    
	    System.out.println("While Loop Implementation:");
	    int i = 1;
	    while (i <= n)
	    {
	        int j = 1;
	        while (j <= i)
	        {
	            System.out.print(j + " ");
	            j++;
	        }
	        System.out.println(); 
	        i++;
	    }
	    
	    
	    System.out.println("\nDo-While Loop Implementation:");
	    i = 1;
	    do
	    {
	        int j = 1;
	        do
	        {
	            System.out.print(j + " ");
	            j++;
	        } while (j <= i);
	        
	        System.out.println();
	        i++;
	        
	    } while (i <= n);
	    
	    System.out.println("\nFor Loop Implementation:");
	    for (int a = 1; a <= n; a++)
	    {
	        for (int j = 1; j <= a; j++)
	        {
	            System.out.print(j + " ");
	        }
	        System.out.println(); 
	    }
	}

	public static void main(String[] args) 
	{
		
		// 1. Task 1 - blackJack Test Code 
		System.out.println(blackJack(1,2));
		System.out.println(blackJack(21,22));
		System.out.println(blackJack(22,22));
		System.out.println(blackJack(2,10));
		
		System.out.println();
		
		// 2. Task 2
		dayWeekSwitch();
		
		System.out.println();
		
		// 3. Task 3 
		dayWeekPattern();
		
		System.out.println();
		
		// 4. Task 4
		stairCount();
	}

}
