package com.tirwanda.react.spring.controller;

import com.tirwanda.react.spring.entity.Employee;
import com.tirwanda.react.spring.exception.ResourceNotFoundException;
import com.tirwanda.react.spring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000/") // Allow to Access from localhost
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Employee Not Exist with id : " + id));

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeReq) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Employee Not Exist with id : " + id));
        employee.setFirstName(employeeReq.getFirstName());
        employee.setLastName(employeeReq.getLastName());
        employee.setEmail(employeeReq.getEmail());

        Employee employeeUpdated = employeeRepository.save(employee);

        return ResponseEntity.ok(employeeUpdated);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe Not Exist with id : " + id));

        Map<String, Boolean> response = new HashMap<>();
        employeeRepository.delete(employee);
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
