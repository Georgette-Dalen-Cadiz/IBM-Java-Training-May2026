package org.eclipse.jakarta.backingbean;

import org.eclipse.jakarta.dto.ReportDto;
import org.eclipse.jakarta.infrastructure.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportEditBeanTest {

    private ReportEditBean bean;
    private List<ReportDto> reports;

    @BeforeEach
    void setUp() throws Exception {
        bean = new ReportEditBean();

        ReportDto existing = new ReportDto();
        existing.setTitle("Original Title");
        existing.setDetail("Original Detail");
        reports = new ArrayList<>(List.of(existing));

        ReportRepository fakeRepo = new ReportRepository() {
            @Override public List<ReportDto> findAll() { return reports; }
            @Override public void create(ReportDto r) {}
            @Override public void delete(ReportDto r) { reports.remove(r); }
            @Override public void update(int index, ReportDto r) { reports.set(index, r); }
        };

        inject(bean, "reportRepository", fakeRepo);
    }

    @Test
    void shouldUpdateReportAndReturnRedirect() {
        bean.setIndex(0);
        bean.setTitle("Updated Title");
        bean.setDetail("Updated Detail");

        String result = bean.update();

        assertEquals("/reportList.xhtml?faces-redirect=true", result);
        assertEquals("Updated Title", reports.get(0).getTitle());
        assertEquals("Updated Detail", reports.get(0).getDetail());
    }

    @Test
    void shouldSetAndGetTitle() {
        bean.setTitle("Hello");
        assertEquals("Hello", bean.getTitle());
    }

    @Test
    void shouldSetAndGetDetail() {
        bean.setDetail("World");
        assertEquals("World", bean.getDetail());
    }

    @Test
    void shouldSetAndGetIndex() {
        bean.setIndex(5);
        assertEquals(5, bean.getIndex());
    }

    private void inject(Object target, String fieldName, Object value) throws Exception {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}