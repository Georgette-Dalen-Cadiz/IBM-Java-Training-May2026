package mypackage;

import java.util.ArrayList;

public class Library {
	
	ArrayList<Book> books = new ArrayList<>();

	public void addBook(Book b)
	{
	     books.add(b);  
	}
	
	
	public void showAllBooks()
	{
		for(Book book : books)
		{
			book.getInfo();
		}
		
		System.out.println();
	}
	
	public void borrowBook(String title)
	{
		for(Book book : books)
		{
			if (book.getTitle() == title)
			{
				book.borrowBook();
			}
			
		}
	}
	
	public void returnBook(String title)
	{
		for(Book book : books)
		{
			if (book.getTitle() == title)
			{
				book.returnBook();
			}
			
		}
	}

}
