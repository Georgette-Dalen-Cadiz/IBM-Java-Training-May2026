package org.eclipse.jakarta.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeDtoTest {

    @Test
    void shouldSetAndGetAllFields() {
        EmployeeDto emp = new EmployeeDto();
        emp.setId(42L);
        emp.setName("Alice Cooper");
        emp.setDetail("Rock singer");
        emp.setEmail("alice@cooper.com");

        assertEquals(42L, emp.getId());
        assertEquals("Alice Cooper", emp.getName());
        assertEquals("Rock singer", emp.getDetail());
        assertEquals("alice@cooper.com", emp.getEmail());
    }

    @Test
    void shouldHaveNullValuesWhenCreated() {
        EmployeeDto emp = new EmployeeDto();
        assertNull(emp.getId());
        assertNull(emp.getName());
        assertNull(emp.getDetail());
        assertNull(emp.getEmail());
    }
}