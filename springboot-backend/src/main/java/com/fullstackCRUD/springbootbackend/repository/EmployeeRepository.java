package com.fullstackCRUD.springbootbackend.repository;

import com.fullstackCRUD.springbootbackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
