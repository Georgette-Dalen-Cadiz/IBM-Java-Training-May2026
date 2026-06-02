package org.eclipse.jakarta.backingbean;

import org.eclipse.jakarta.dto.ReportDto;
import org.eclipse.jakarta.infrastructure.repository.ReportRepository;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ReportNewBeanTest {

    private ReportNewBean bean;
    private ReportRepository fakeRepo;
    private ReportDto capturedReport;

    @BeforeEach
    void setUp() {
        bean = new ReportNewBean();

        fakeRepo = new ReportRepository() {
            @Override
            public void create(ReportDto report) {
                capturedReport = report;
            }
        };

        setRepository(bean, fakeRepo);

        System.out.println("@BeforeEach: Test setup completed");
    }

    @AfterEach
    void tearDown() {
        bean = null;
        fakeRepo = null;
        capturedReport = null;

        System.out.println("@AfterEach: Test cleanup completed");
    }

    @Test
    @DisplayName("Should create report and return redirect")
    void shouldCreateReportAndReturnRedirect() {
        bean.setTitle("Test Title");
        bean.setDetail("Test Detail");

        String result = bean.create();

        assertEquals("/reportList.xhtml?faces-redirect=true", result);

        assertNotNull(capturedReport);
        assertEquals("Test Title", capturedReport.getTitle());
        assertEquals("Test Detail", capturedReport.getDetail());
    }

    @Test
    @DisplayName("Should set and get fields correctly")
    void shouldSetAndGetFields() {
        bean.setTitle("Title");
        bean.setDetail("Detail");

        assertEquals("Title", bean.getTitle());
        assertEquals("Detail", bean.getDetail());
    }

    private void setRepository(ReportNewBean bean, ReportRepository repo) {
        try {
            var field = ReportNewBean.class.getDeclaredField("reportRepository");
            field.setAccessible(true);
            field.set(bean, repo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}