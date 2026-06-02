package org.eclipse.jakarta.backingbean;

import org.eclipse.jakarta.dto.ReportDto;
import org.eclipse.jakarta.infrastructure.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportViewBeanTest {

    private ReportViewBean bean;
    private List<ReportDto> reports;

    @BeforeEach
    void setUp() throws Exception {
        bean = new ReportViewBean();

        ReportDto r = new ReportDto();
        r.setTitle("View Title");
        r.setDetail("View Detail");

        reports = new ArrayList<>(List.of(r));

        ReportRepository fakeRepo = new ReportRepository() {
            @Override public List<ReportDto> findAll() { return reports; }
            @Override public void create(ReportDto r) {}
            @Override public void delete(ReportDto r) {}
            @Override public void update(int i, ReportDto r) {}
        };

        inject(bean, "reportRepository", fakeRepo);
    }

    @Test
    void shouldLoadReportFieldsOnInit() {
        bean.setIndex(0);
        bean.init();

        assertEquals("View Title", bean.getTitle());
        assertEquals("View Detail", bean.getDetail());
    }

    @Test
    void shouldSetAndGetIndex() {
        bean.setIndex(3);
        assertEquals(3, bean.getIndex());
    }

    @Test
    void shouldSetAndGetTitle() {
        bean.setTitle("New Title");
        assertEquals("New Title", bean.getTitle());
    }

    @Test
    void shouldSetAndGetDetail() {
        bean.setDetail("New Detail");
        assertEquals("New Detail", bean.getDetail());
    }

    private void inject(Object target, String fieldName, Object value) throws Exception {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}