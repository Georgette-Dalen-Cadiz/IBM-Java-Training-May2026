package org.eclipse.jakarta.infrastructure.repository;

import org.eclipse.jakarta.dto.EmployeeDto;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EmployeeRepository {

    @Resource(lookup = "java:global/EmployeeDS")
    private DataSource dataSource;

    public List<EmployeeDto> findAll() {
        List<EmployeeDto> employees = new ArrayList<>();
        String sql = "SELECT id, name, detail, email FROM employees ORDER BY id";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                EmployeeDto emp = new EmployeeDto();
                emp.setId(rs.getLong("id"));
                emp.setName(rs.getString("name"));
                emp.setDetail(rs.getString("detail"));
                emp.setEmail(rs.getString("email"));
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // or log properly
        }
        return employees;
    }

    public EmployeeDto findById(Long id) {
        String sql = "SELECT id, name, detail, email FROM employees WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    EmployeeDto emp = new EmployeeDto();
                    emp.setId(rs.getLong("id"));
                    emp.setName(rs.getString("name"));
                    emp.setDetail(rs.getString("detail"));
                    emp.setEmail(rs.getString("email"));
                    return emp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void create(EmployeeDto employee) {
        String sql = "INSERT INTO employees (name, detail, email) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getDetail());
            ps.setString(3, employee.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(EmployeeDto employee) {
        String sql = "UPDATE employees SET name = ?, detail = ?, email = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getDetail());
            ps.setString(3, employee.getEmail());
            ps.setLong(4, employee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(EmployeeDto employee) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, employee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EmployeeDto> searchByName(String keyword) {
        List<EmployeeDto> employees = new ArrayList<>();
        String sql = "SELECT id, name, detail, email FROM employees WHERE name ILIKE ? ORDER BY id";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmployeeDto emp = new EmployeeDto();
                    emp.setId(rs.getLong("id"));
                    emp.setName(rs.getString("name"));
                    emp.setDetail(rs.getString("detail"));
                    emp.setEmail(rs.getString("email"));
                    employees.add(emp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // replace with logger later
        }
        return employees;
    }
}