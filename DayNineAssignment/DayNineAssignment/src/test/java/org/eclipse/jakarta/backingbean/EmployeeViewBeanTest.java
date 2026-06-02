package org.eclipse.jakarta.backingbean;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeViewBeanTest {
    @Test
    void testGetEmployeeDefault() {
        EmployeeViewBean bean = new EmployeeViewBean();
        assertNull(bean.getEmployee());
    }
}