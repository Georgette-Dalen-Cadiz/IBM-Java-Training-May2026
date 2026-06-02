package org.eclipse.jakarta.backingbean;

import org.eclipse.jakarta.dto.ReportDto;
import org.eclipse.jakarta.infrastructure.repository.ReportRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;

@Named
@RequestScoped
public class ReportViewBean {

    @Inject
    private ReportRepository reportRepository;

    private int index;
    private String title;
    private String detail;

    public void init() {
        ReportDto report = reportRepository.findAll().get(index);
        this.title = report.getTitle();
        this.detail = report.getDetail();
    }

    public int getIndex() {
    	return index; 
    }
    
    public void setIndex(int index) { 
    	this.index = index; 
    }

    public String getTitle() { 
    	return title; 
    }
    
    public void setTitle(String title) { 
    	this.title = title; 
    }

    public String getDetail() { 
    	return detail; 
    }
    
    public void setDetail(String detail) { 
    	this.detail = detail; 
    }
}