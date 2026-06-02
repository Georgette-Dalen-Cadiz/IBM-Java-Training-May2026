package org.eclipse.jakarta.infrastructure.repository;

import org.eclipse.jakarta.dto.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {

    private EmployeeRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        // In-memory H2 DataSource
        org.h2.jdbcx.JdbcDataSource ds = new org.h2.jdbcx.JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");

        // Create table (if not exists) and clear any leftover data
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS employees (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    detail VARCHAR(255),
                    email VARCHAR(100)
                )
            """);
            stmt.execute("DELETE FROM employees"); // Clear all rows before each test
        }

        // Inject DataSource into repository
        repository = new EmployeeRepository();
        try {
            var field = EmployeeRepository.class.getDeclaredField("dataSource");
            field.setAccessible(true);
            field.set(repository, ds);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject DataSource", e);
        }
    }

    @Test
    void testCreateAndFindById() {
        EmployeeDto emp = new EmployeeDto();
        emp.setName("John");
        emp.setDetail("Developer");
        emp.setEmail("john@test.com");
        repository.create(emp);

        List<EmployeeDto> all = repository.findAll();
        assertEquals(1, all.size());
        EmployeeDto saved = all.get(0);
        assertNotNull(saved.getId());
        assertEquals("John", saved.getName());

        EmployeeDto found = repository.findById(saved.getId());
        assertNotNull(found);
    }

    @Test
    void testUpdate() {
        EmployeeDto emp = new EmployeeDto();
        emp.setName("Old");
        repository.create(emp);
        List<EmployeeDto> list = repository.findAll();
        assertEquals(1, list.size());

        EmployeeDto saved = list.get(0);
        saved.setName("Updated");
        repository.update(saved);

        EmployeeDto updated = repository.findById(saved.getId());
        assertEquals("Updated", updated.getName());
    }

    @Test
    void testDelete() {
        EmployeeDto emp = new EmployeeDto();
        emp.setName("ToDelete");
        repository.create(emp);

        List<EmployeeDto> before = repository.findAll();
        assertEquals(1, before.size());

        repository.delete(before.get(0));

        List<EmployeeDto> after = repository.findAll();
        assertTrue(after.isEmpty());
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(repository.findById(999L));
    }

    @Test
    void testSearchByName() {
        EmployeeDto e = new EmployeeDto();
        e.setName("Alice Wonder");
        repository.create(e);

        List<EmployeeDto> results = repository.searchByName("ali");
        assertEquals(1, results.size());
        assertEquals("Alice Wonder", results.get(0).getName());
    }
}