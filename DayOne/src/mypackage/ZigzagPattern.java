package mypackage;

import java.lang.*;
import java.util.*;

public class ZigzagPattern {

	public static void main(String[] args) {
		int a;
		System.out.print("Enter a number: ");
		Scanner sc = new Scanner(System.in);
		a = sc.nextInt();
		
		int tar = a * a;
		int res = 0; 
		
		
		for(int i = 1; i < tar; i++) {
			if (res < a)
			{
				System.out.print(i + (" "));
				res += 1;
			}
			else
			{
				res = i + a - 1;
				System.out.println();
				for(int j = 0; j < a; j ++)
				{
					System.out.print(res + (" "));
					res -= 1;
				}
				i += a - 1;
			}
		}
		
		
	}

}
