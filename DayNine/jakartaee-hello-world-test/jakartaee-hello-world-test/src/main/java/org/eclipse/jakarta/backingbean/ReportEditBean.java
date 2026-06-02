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
public class ReportEditBean {

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private FacesContext facesContext;

    private String title;
    private String detail;
    private int index;

    @PostConstruct
    public void init() {
        String indexStr = facesContext.getExternalContext().getRequestParameterMap().get("index");

        if (indexStr != null) {
            index = Integer.parseInt(indexStr);
            ReportDto report = reportRepository.findAll().get(index);
            this.title = report.getTitle();
            this.detail = report.getDetail();
        }
    }

    public String update() {
        ReportDto report = reportRepository.findAll().get(index);
        report.setTitle(title);
        report.setDetail(detail);
        reportRepository.update(index, report);
        return "/reportList.xhtml?faces-redirect=true";
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

    public int getIndex() { 
    	return index;
    }
    
    public void setIndex(int index) { 
    	this.index = index;
    }
}