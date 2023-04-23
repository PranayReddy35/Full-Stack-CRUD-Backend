package com.fullstackCRUD.springbootbackend.controller;

import com.fullstackCRUD.springbootbackend.exception.ResourceNotFoundException;
import com.fullstackCRUD.springbootbackend.model.Employee;
import com.fullstackCRUD.springbootbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employees
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(employeeList);
    }

    //get employee by Id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee exists with given Id : " + id));
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    //adding new employee
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    //update employee by Id
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable("id") Long id) {
        Employee employeeToBeUpdated = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee exists with given Id : " + id));

        employeeToBeUpdated.setFirstName(employee.getFirstName());
        employeeToBeUpdated.setLastName(employee.getLastName());
        employeeToBeUpdated.setEmailId(employee.getEmailId());

        Employee updatedAndSavedEmployee = employeeRepository.save(employeeToBeUpdated);

        return ResponseEntity.status(HttpStatus.OK).body(updatedAndSavedEmployee);
    }

    //delete employee
    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable("id") Long id) {
        Employee employeeToBeDeleted = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee exists with given Id : " + id));
        employeeRepository.delete(employeeToBeDeleted);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.status(HttpStatus.OK).body(response).getBody();
    }
}
