package org.eclipse.jakarta.backingbean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeEditBeanTest {

    private EmployeeEditBean bean;

    @BeforeEach
    void setUp() {
        bean = new EmployeeEditBean();
    }

    @Test
    void testGettersSetters() {
        bean.setId(10L);
        bean.setName("Test Name");
        bean.setDetail("Test Detail");
        bean.setEmail("test@example.com");

        assertEquals(10L, bean.getId());
        assertEquals("Test Name", bean.getName());
        assertEquals("Test Detail", bean.getDetail());
        assertEquals("test@example.com", bean.getEmail());
    }
}