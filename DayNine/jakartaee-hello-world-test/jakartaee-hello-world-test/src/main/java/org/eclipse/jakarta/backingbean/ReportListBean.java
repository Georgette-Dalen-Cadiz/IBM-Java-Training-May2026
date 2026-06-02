package org.eclipse.jakarta.backingbean;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jakarta.dto.ReportDto;
import org.eclipse.jakarta.infrastructure.repository.ReportRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ReportListBean {

    private List<ReportDto> reports;

    @Inject
    private ReportRepository reportRepository;

    @PostConstruct
    public void init() {
        reports = reportRepository.findAll();
    }

    public List<ReportDto> getReports() {
        return reports;
    }

    public void setReports(List<ReportDto> reports) {
        this.reports = reports;
    }

    public void delete(ReportDto report) {
        reportRepository.delete(report);
        reports = reportRepository.findAll(); 
    }
}