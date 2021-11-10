package com.springbootproject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springbootproject.exception.ResourceNotFoundException;
import com.springbootproject.model.Employee;
import com.springbootproject.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



// within controller we create rest api's
@CrossOrigin( origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")  // standard url endpoints that we user in rest api's
public class EmployeeController {
    
    // injecting employee repository

    @Autowired // to inject repository by spring container
    private  EmployeeRepository employeeRepository;
    
    // get all employees
    
    @GetMapping("/employees")  // when we hit in browser local host /api/vi/employee the list is called
    public List<Employee> getAllEmployee()
    {
        return employeeRepository.findAll();
    }
    

//  create employee rest api
    @PostMapping("/employee") 
    public Employee createEmployee(@RequestBody Employee employee)
   {
     return employeeRepository.save(employee);      // method created
    }

    // get employee by id rest api
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        
        return  ResponseEntity.ok(employee);

    }

    // update employee Rest api
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails)
    {
        // for retriving existing data from the database
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
    
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

         Employee updateEmployee = employeeRepository.save(employee);
         return ResponseEntity.ok(updateEmployee);
    
    }

    // delete emoloyee rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>>deleteEmployee(Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}