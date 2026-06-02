package org.eclipse.jakarta.backingbean;

import org.eclipse.jakarta.dto.EmployeeDto;
import org.eclipse.jakarta.infrastructure.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class EmployeeViewBean {

    @Inject
    private EmployeeRepository employeeRepository;
    @Inject
    private FacesContext facesContext;

    private EmployeeDto employee;

    @PostConstruct
    public void init() {
        String idParam = facesContext.getExternalContext().getRequestParameterMap().get("id");
        if (idParam != null) {
            Long id = Long.parseLong(idParam);
            employee = employeeRepository.findById(id);
        }
    }

    public EmployeeDto getEmployee() {
        return employee;
    }
}