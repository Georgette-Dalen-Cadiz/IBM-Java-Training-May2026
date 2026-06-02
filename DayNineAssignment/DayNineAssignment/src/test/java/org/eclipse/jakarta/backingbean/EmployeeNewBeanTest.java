package org.eclipse.jakarta.backingbean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeNewBeanTest {

    private EmployeeNewBean bean;

    @BeforeEach
    void setUp() {
        bean = new EmployeeNewBean();
    }

    @Test
    void testGettersSetters() {
        bean.setName("Alice");
        bean.setDetail("Engineer");
        bean.setEmail("alice@example.com");

        assertEquals("Alice", bean.getName());
        assertEquals("Engineer", bean.getDetail());
        assertEquals("alice@example.com", bean.getEmail());
    }
}