package org.eclipse.jakarta.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportDtoTest {

    @Test
    void shouldSetAndGetTitle() {
        ReportDto dto = new ReportDto();
        dto.setTitle("Test Title");
        assertEquals("Test Title", dto.getTitle());
    }

    @Test
    void shouldSetAndGetDetail() {
        ReportDto dto = new ReportDto();
        dto.setDetail("Test Detail");

        assertEquals("Test Detail", dto.getDetail());
    }

    @Test
    void shouldCreateUsingConstructor() {
        ReportDto dto = new ReportDto("Title", "Detail");

        assertEquals("Title", dto.getTitle());
        assertEquals("Detail", dto.getDetail());
    }

    @Test
    void shouldHandleNullValues() {
        ReportDto dto = new ReportDto();

        dto.setTitle(null);
        dto.setDetail(null);

        assertNull(dto.getTitle());
        assertNull(dto.getDetail());
    }

    @Test
    void shouldHandleEmptyStrings() {
        ReportDto dto = new ReportDto("", "");

        assertEquals("", dto.getTitle());
        assertEquals("", dto.getDetail());
    }

    @Test
    void shouldUpdateValuesCorrectly() {
        ReportDto dto = new ReportDto();

        dto.setTitle("Old");
        dto.setDetail("Old Detail");

        dto.setTitle("New");
        dto.setDetail("New Detail");

        assertEquals("New", dto.getTitle());
        assertEquals("New Detail", dto.getDetail());
    }
}