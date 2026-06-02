package org.eclipse.jakarta.infrastructure.repository;

import org.eclipse.jakarta.dto.ReportDto;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportRepositoryTest {

    private ReportRepository repository;
    private ReportDto testReport1;
    private ReportDto testReport2;

    @BeforeEach
    void setUp() {
        repository = new ReportRepository();

        testReport1 = new ReportDto();
        testReport1.setTitle("Test Report 1");
        testReport1.setDetail("This is the first test report");

        testReport2 = new ReportDto();
        testReport2.setTitle("Test Report 2");
        testReport2.setDetail("This is the second test report");

        System.out.println("@BeforeEach: Test setup completed");
    }

    @AfterEach
    void tearDown() {
        repository = null;
        testReport1 = null;
        testReport2 = null;
        System.out.println("@AfterEach: Test cleanup completed");
    }

    @Test
    @DisplayName("Should create a new report with auto-generated ID")
    void testCreate() {
        repository.create(testReport1);

        assertNotNull(testReport1.getId());
        assertEquals(1L, testReport1.getId());

        List<ReportDto> reports = repository.findAll();
        assertEquals(1, reports.size());
    }

    @Test
    @DisplayName("Should store multiple reports")
    void testCreateMultiple() {
        repository.create(testReport1);
        repository.create(testReport2);

        List<ReportDto> reports = repository.findAll();

        assertEquals(2, reports.size());
        assertEquals("Test Report 1", reports.get(0).getTitle());
        assertEquals("Test Report 2", reports.get(1).getTitle());
    }

    @Test
    @DisplayName("Should return empty list initially")
    void testFindAllEmpty() {
        List<ReportDto> reports = repository.findAll();

        assertTrue(reports.isEmpty());
    }
}