package mypackage;

import java.sql.*;
import java.util.Scanner;

public class Main {
	
	static String url = "jdbc:postgresql://localhost:5432/postgres";
	static String user = "georgette";
	static String password = "password";

    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        char choice;
        
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("[A] Add");
            System.out.println("[V] View");
            System.out.println("[U] Update Password");
            System.out.println("[D] Delete");
            System.out.println("[Q] Quit");
            System.out.print("Enter choice: ");

            choice = sc.next().toUpperCase().charAt(0);

            switch (choice) {
                case 'A':
                    addStudent();
                    break;
                case 'V':
                    viewStudents();
                    break;
                case 'U':
                    updatePassword();
                    break;
                case 'D':
                    deleteStudent();
                    break;
                case 'Q':
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 'Q');

        sc.close();
    }
    
    static void addStudent()
    {
    	String sql = "INSERT INTO student (studentid, email, password, firstname, lastname, dateadded, dateupdated) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
    	
    	try (Connection conn = DriverManager.getConnection(url, user, password);
    			PreparedStatement ps = conn.prepareStatement(sql)) {
    		
    		Scanner sc = new Scanner(System.in);
    		
    		int id;
    		System.out.print("Enter ID: ");
    		id = sc.nextInt();
    		sc.nextLine();
    		
    		String email, password, firstname, lastname;
    		System.out.print("Enter Email: ");
    		email = sc.nextLine();
    		
    		System.out.print("Enter Password: ");
    		password = sc.nextLine();
    		
    		System.out.print("Enter First Name: ");
    		firstname = sc.nextLine();
    		
    		System.out.print("Enter Last Name: ");
    		lastname = sc.nextLine();
    		
    		ps.setInt(1, id);
    		ps.setString(2, email);
    		ps.setString(3, password);
    		ps.setString(4, firstname);
    		ps.setString(5, lastname);
    		
    		ps.executeUpdate();
            System.out.println("Student added!");
    		
    		
    	} catch (Exception e) {
    		
    	}
    }
    
    static void viewStudents()
    {
    	String sql = "SELECT * FROM student";
    	try (Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
           	
               while (rs.next()) {
                   int id = rs.getInt("studentid");
                   String email = rs.getString("email");
                   String firstName = rs.getString("firstname");
                   String lastName = rs.getString("lastname");

                   System.out.println(id + " | " + email + " | " + firstName + " | " + lastName);
               }

           } catch (Exception e) {
               e.printStackTrace();
           }
    }
    
    static void updatePassword()
    {
    	String sql = "UPDATE student SET password = ? WHERE email = ?;";
    	
    	try (Connection conn = DriverManager.getConnection(url, user, password);
    			PreparedStatement ps = conn.prepareStatement(sql)) {
    		
    		Scanner sc = new Scanner(System.in);
    		String email, password;
    		System.out.print("Enter Email: ");
    		email = sc.nextLine();
    		
    		System.out.print("Enter New Password: ");
    		password = sc.nextLine();
    		
    		ps.setString(1, password);
    		ps.setString(2, email);
    		
    		ps.executeUpdate();
            System.out.println("Student Password Updated!");
    		
    	} catch (Exception e) {
    		
    	}
    }
    
    static void deleteStudent()
    {
    	String sql = "DELETE FROM student WHERE studentid = ?";
    	
    	try (Connection conn = DriverManager.getConnection(url, user, password);
    			PreparedStatement ps = conn.prepareStatement(sql)) {
    		
    		Scanner sc = new Scanner(System.in);
    		int id;
    		System.out.print("Enter ID: ");
    		id = sc.nextInt();
    		sc.nextLine();
    		
    		ps.setInt(1, id);
    		
    		ps.executeUpdate();
            System.out.println("Student deleted!");
    	} catch (Exception e) {
    		
    	}
    }
}