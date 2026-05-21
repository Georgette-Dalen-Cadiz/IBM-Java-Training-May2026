package mypackage;

public class Book {
	
	private String title, author;
	private boolean available = true;
	
	public Book(String title, String author, boolean available)
	{
		this.title = title;
		this.author = author;
		this.available = available;
	}
	
	public Book(String title)
	{
		this.title = title;
		this.author = "Unknown";
		this.available = true;
	}
	
	public Book(String title, String author)
	{
		this.title = title;
		this.author = author;
		this.available = true;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void borrowBook() 
	{
		if (available == true)
		{
			available = false;
		} 
		else 
		{
			System.out.println("Book is already borrowed.");
		}
	}
	
	public void returnBook()
	{
		available = true;
	}
	
	public void getInfo()
	{
		System.out.println("Book Information:");
		System.out.println(title);
		System.out.println(author);
		System.out.println(available);
	}

}
