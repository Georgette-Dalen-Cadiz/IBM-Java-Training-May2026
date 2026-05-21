package mypackage;

public class Main {

	public static void main(String[] args) {
		// Create a Library object
		
		Library myLib = new Library();
		
		Book b1 = new Book("The Hunger Games", "Suzanne Collins", true);
		Book b2 = new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", true);
		Book b3 = new Book("The Little Prince");

		myLib.addBook(b1);
		myLib.addBook(b2);
		myLib.addBook(b3);
		
		System.out.println("Adding Books");
		myLib.showAllBooks();
		
		myLib.borrowBook("The Hunger Games");
		
		System.out.println("Borrowing a Book");
		myLib.showAllBooks();
		
		myLib.returnBook("The Hunger Games");
		
		System.out.println("Returning a Book");
		myLib.showAllBooks();

	}

}
