package org.eclipse.jakarta.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;

@DataSourceDefinition(
    name = "java:global/EmployeeDS",
    className = "org.postgresql.ds.PGSimpleDataSource",
    url = "jdbc:postgresql://localhost:5432/postgres",   
    user = "georgette",                                  
    password = "password"
)
@ApplicationScoped
public class DatabaseConfig {
    // This bean just makes the DataSource definition available
}