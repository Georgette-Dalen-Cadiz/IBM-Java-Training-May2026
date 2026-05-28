package mypackage;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "georgette";
        String password = "password";

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
}