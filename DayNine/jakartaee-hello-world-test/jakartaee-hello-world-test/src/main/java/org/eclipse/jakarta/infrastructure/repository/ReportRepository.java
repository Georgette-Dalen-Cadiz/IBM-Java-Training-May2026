package org.eclipse.jakarta.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jakarta.dto.ReportDto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReportRepository {
	
	private int nextId = 1;

    private List<ReportDto> reports = new ArrayList<>();

    public List<ReportDto> findAll() {
        return reports;
    }

    public void create(ReportDto report) {
        report.setId((long) nextId++);  // assign an id before storing
        reports.add(report);
    }

    public void delete(ReportDto report) {
        reports.remove(report);
    }

    public void update(int index, ReportDto updatedReport) {
        if (index >= 0 && index < reports.size()) {
            reports.set(index, updatedReport);
        }
    }
}