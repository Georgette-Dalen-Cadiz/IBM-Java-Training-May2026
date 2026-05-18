package mypackage;

import java.lang.*;
import java.util.*;

public class DayOne {
	
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

	public static void main(String[] args) 
	{
//		int a, b, res;
//		Scanner sc = new Scanner(System.in);
//		
//		a = sc.nextInt();
//		b = sc.nextInt();
//		res = blackJack(a, b);
		System.out.println(blackJack(1,2));
		System.out.println(blackJack(21,22));
		System.out.println(blackJack(22,22));
		System.out.println(blackJack(2,10));
	}

}
