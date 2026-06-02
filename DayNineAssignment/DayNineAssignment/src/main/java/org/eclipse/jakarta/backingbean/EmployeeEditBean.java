package org.eclipse.jakarta.backingbean;

import org.eclipse.jakarta.dto.EmployeeDto;
import org.eclipse.jakarta.infrastructure.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class EmployeeEditBean implements Serializable {

    @Inject
    private EmployeeRepository employeeRepository;
    @Inject
    private FacesContext facesContext;

    private Long id;
    private String name;
    private String detail;
    private String email;

    @PostConstruct
    public void init() {
        String idParam = facesContext.getExternalContext().getRequestParameterMap().get("id");
        if (idParam != null && !idParam.isEmpty()) {
            id = Long.parseLong(idParam);
            EmployeeDto emp = employeeRepository.findById(id);
            if (emp != null) {
                this.name = emp.getName();
                this.detail = emp.getDetail();
                this.email = emp.getEmail();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Employee not found."));
            }
        }
    }

    public String update() {
        if (id == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No employee selected."));
            return null;
        }
        try {
            EmployeeDto emp = new EmployeeDto();
            emp.setId(id);
            emp.setName(name);
            emp.setDetail(detail);
            emp.setEmail(email);
            employeeRepository.update(emp);
            FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().put("successMessage", "Employee updated successfully.");
            return "/employeeList.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Update failed: " + e.getMessage()));
            return null;
        }
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}