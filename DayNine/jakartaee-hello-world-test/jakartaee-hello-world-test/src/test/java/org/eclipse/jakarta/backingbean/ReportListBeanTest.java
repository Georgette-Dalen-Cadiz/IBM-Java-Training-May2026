package org.eclipse.jakarta.backingbean;

import org.eclipse.jakarta.dto.ReportDto;
import org.eclipse.jakarta.infrastructure.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportListBeanTest {

    private ReportListBean bean;
    private List<ReportDto> reports;

    @BeforeEach
    void setUp() throws Exception {
        bean = new ReportListBean();

        ReportDto r1 = new ReportDto();
        r1.setTitle("Report 1");
        r1.setDetail("Detail 1");

        ReportDto r2 = new ReportDto();
        r2.setTitle("Report 2");
        r2.setDetail("Detail 2");

        reports = new ArrayList<>(List.of(r1, r2));

        ReportRepository fakeRepo = new ReportRepository() {
            @Override public List<ReportDto> findAll() { return reports; }
            @Override public void create(ReportDto r) { reports.add(r); }
            @Override public void delete(ReportDto r) { reports.remove(r); }
            @Override public void update(int i, ReportDto r) { reports.set(i, r); }
        };

        inject(bean, "reportRepository", fakeRepo);
    }

    @Test
    void shouldLoadAllReportsOnInit() {
        bean.init();

        assertNotNull(bean.getReports());
        assertEquals(2, bean.getReports().size());
        assertEquals("Report 1", bean.getReports().get(0).getTitle());
        assertEquals("Report 2", bean.getReports().get(1).getTitle());
    }

    @Test
    void shouldDeleteReportAndRefreshList() {
        bean.init();
        ReportDto toDelete = bean.getReports().get(0);

        bean.delete(toDelete);

        assertEquals(1, bean.getReports().size());
        assertEquals("Report 2", bean.getReports().get(0).getTitle());
    }

    @Test
    void shouldReturnEmptyListWhenNoReports() {
        reports.clear();
        bean.init();

        assertNotNull(bean.getReports());
        assertTrue(bean.getReports().isEmpty());
    }

    private void inject(Object target, String fieldName, Object value) throws Exception {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}