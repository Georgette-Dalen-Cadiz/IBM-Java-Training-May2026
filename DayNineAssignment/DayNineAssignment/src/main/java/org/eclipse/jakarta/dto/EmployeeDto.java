package org.eclipse.jakarta.dto;

import java.io.Serializable;

public class EmployeeDto implements Serializable {
    private Long id;          
    private String name;      
    private String detail;    
    private String email;     

    // constructors, getters, setters
    public EmployeeDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}