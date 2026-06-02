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
import java.util.List;

@Named
@ViewScoped
public class EmployeeListBean implements Serializable {

    private List<EmployeeDto> employees;
    private String searchKeyword;
    private Long deleteId;  // store ID of employee to delete

    @Inject
    private EmployeeRepository employeeRepository;

    @PostConstruct
    public void init() {
        System.out.println("EmployeeListBean initialized");
        employees = employeeRepository.findAll();
    }

    // Called when the trash icon is clicked
    public void prepareDelete(EmployeeDto employee) {
        System.out.println("prepareDelete called for employee ID: " + employee.getId());
        this.deleteId = employee.getId();
    }

    // Called when "Yes, Delete" is clicked
    public void confirmDelete() {
        System.out.println("confirmDelete called, deleteId = " + deleteId);
        if (deleteId != null) {
            try {
                EmployeeDto emp = employeeRepository.findById(deleteId);
                if (emp != null) {
                    employeeRepository.delete(emp);
                    employees = employeeRepository.findAll(); // refresh list
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Employee deleted."));
                    System.out.println("Employee " + deleteId + " deleted successfully.");
                } else {
                    System.out.println("Employee with ID " + deleteId + " not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Delete failed: " + e.getMessage()));
            } finally {
                deleteId = null;
            }
        } else {
            System.out.println("deleteId is NULL – cannot delete.");
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No employee selected."));
        }
    }

    public void search() {
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            employees = employeeRepository.findAll();
        } else {
            employees = employeeRepository.searchByName(searchKeyword.trim());
        }
    }

    public void checkFlashMessage() {
        String msg = (String) FacesContext.getCurrentInstance().getExternalContext()
            .getFlash().get("successMessage");
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
        }
    }

    // Getters and setters
    public List<EmployeeDto> getEmployees() { return employees; }
    public void setEmployees(List<EmployeeDto> employees) { this.employees = employees; }
    public String getSearchKeyword() { return searchKeyword; }
    public void setSearchKeyword(String searchKeyword) { this.searchKeyword = searchKeyword; }
    public Long getDeleteId() { return deleteId; }
    public void setDeleteId(Long deleteId) { this.deleteId = deleteId; }
}