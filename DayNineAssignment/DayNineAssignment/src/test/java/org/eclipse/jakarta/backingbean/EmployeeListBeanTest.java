package org.eclipse.jakarta.backingbean;

import org.eclipse.jakarta.dto.EmployeeDto;
import org.eclipse.jakarta.infrastructure.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeListBeanTest {

    private EmployeeListBean bean;
    private List<EmployeeDto> employees;

    @BeforeEach
    void setUp() throws Exception {
        bean = new EmployeeListBean();

        EmployeeDto e1 = new EmployeeDto();
        e1.setId(1L);
        e1.setName("John Doe");
        EmployeeDto e2 = new EmployeeDto();
        e2.setId(2L);
        e2.setName("Jane Smith");

        employees = new ArrayList<>(List.of(e1, e2));

        EmployeeRepository fakeRepo = new EmployeeRepository() {
            @Override public List<EmployeeDto> findAll() { return employees; }
            @Override public void delete(EmployeeDto emp) { employees.removeIf(e -> e.getId().equals(emp.getId())); }
            @Override public List<EmployeeDto> searchByName(String kw) { return employees; }
            @Override public EmployeeDto findById(Long id) { return null; }
            @Override public void create(EmployeeDto emp) {}
            @Override public void update(EmployeeDto emp) {}
        };
        inject(bean, "employeeRepository", fakeRepo);
    }

    @Test
    void shouldLoadAllEmployeesOnInit() {
        bean.init();
        assertEquals(2, bean.getEmployees().size());
    }

    @Test
    void shouldPrepareDeleteAndStoreId() {
        bean.prepareDelete(employees.get(0));
        assertEquals(1L, bean.getDeleteId());
    }

    @Test
    void testSearch() {
        bean.init();
        bean.setSearchKeyword("john");
        bean.search();
        assertEquals(2, bean.getEmployees().size());
    }

    private static class RecordingFakeRepository extends EmployeeRepository {
        boolean findAllCalled = false;
        boolean searchByNameCalled = false;
        String searchKeyword = null;

        @Override
        public List<EmployeeDto> findAll() {
            findAllCalled = true;
            return new ArrayList<>();
        }

        @Override
        public List<EmployeeDto> searchByName(String keyword) {
            searchByNameCalled = true;
            searchKeyword = keyword;
            return new ArrayList<>();
        }

        @Override public EmployeeDto findById(Long id) { return null; }
        @Override public void create(EmployeeDto emp) {}
        @Override public void update(EmployeeDto emp) {}
        @Override public void delete(EmployeeDto emp) {}
    }

    @Test
    void searchWithNullKeywordShouldCallFindAll() throws Exception {
        EmployeeListBean testBean = new EmployeeListBean();
        RecordingFakeRepository fakeRepo = new RecordingFakeRepository();
        inject(testBean, "employeeRepository", fakeRepo);
        testBean.setSearchKeyword(null);
        testBean.search();
        assertTrue(fakeRepo.findAllCalled);
        assertFalse(fakeRepo.searchByNameCalled);
    }

    @Test
    void searchWithEmptyKeywordShouldCallFindAll() throws Exception {
        EmployeeListBean testBean = new EmployeeListBean();
        RecordingFakeRepository fakeRepo = new RecordingFakeRepository();
        inject(testBean, "employeeRepository", fakeRepo);
        testBean.setSearchKeyword("   ");
        testBean.search();
        assertTrue(fakeRepo.findAllCalled);
        assertFalse(fakeRepo.searchByNameCalled);
    }

    @Test
    void searchWithNonEmptyKeywordShouldCallSearchByName() throws Exception {
        EmployeeListBean testBean = new EmployeeListBean();
        RecordingFakeRepository fakeRepo = new RecordingFakeRepository();
        inject(testBean, "employeeRepository", fakeRepo);
        testBean.setSearchKeyword("John");
        testBean.search();
        assertFalse(fakeRepo.findAllCalled);
        assertTrue(fakeRepo.searchByNameCalled);
        assertEquals("John", fakeRepo.searchKeyword);
    }

    private void inject(Object target, String fieldName, Object value) throws Exception {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}