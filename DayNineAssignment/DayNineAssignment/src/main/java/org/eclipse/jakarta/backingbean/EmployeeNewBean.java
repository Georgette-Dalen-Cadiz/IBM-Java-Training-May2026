package org.eclipse.jakarta.backingbean;

import org.eclipse.jakarta.dto.EmployeeDto;
import org.eclipse.jakarta.infrastructure.repository.EmployeeRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotBlank;

@Named
@RequestScoped
public class EmployeeNewBean {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String detail;
    private String email;

    @Inject
    private EmployeeRepository employeeRepository;

    public String create() {
        try {
            EmployeeDto emp = new EmployeeDto();
            emp.setName(name);
            emp.setDetail(detail);
            emp.setEmail(email);
            employeeRepository.create(emp);

            // Add success message to flash scope for redirect
            FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().put("successMessage", "Employee created successfully!");
            return "/employeeList.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Creation failed: " + e.getMessage()));
            return null; // stay on the same page
        }
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}