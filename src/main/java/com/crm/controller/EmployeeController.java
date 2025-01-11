package com.crm.controller;



import com.crm.entity.Employee;
import com.crm.payload.EmployeeDto;
import com.crm.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    //http://localhost:8080/api/v1/employee/add


    /*@PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(

            @RequestBody Employee employee
            ){
        Employee emp= employeeService.addEmployee(employee);

        return new ResponseEntity<>(emp , HttpStatus.CREATED);


      NOTE=  here we return employee as object but in below we does not  return  employee
      as object we return employee dto as    object
    } */


    //http://localhost:8080/api/v1/employee/add
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(

           @Valid @RequestBody EmployeeDto dto,
           BindingResult result
    ){

        if(result.hasErrors()){
           return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR) ;
        }
        EmployeeDto employeeDto= employeeService.addEmployee(dto);

        return new ResponseEntity<>(employeeDto , HttpStatus.CREATED);
    }

    //http://localhost:8080/api/v1/employee?id=
   @DeleteMapping
    public ResponseEntity<String> deleteEmployee(
            @RequestParam Long id
    ){
        employeeService.delteteEmployee(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

   // http://localhost:8080/api/v1/employee?id
    @PutMapping
    public ResponseEntity<EmployeeDto> UpdateEmployee(
           @RequestParam Long id ,
           @RequestBody EmployeeDto dto
    ){
        EmployeeDto employeeDto=employeeService.updateEmployee(id,dto);
        return new ResponseEntity<>(employeeDto,HttpStatus.ACCEPTED);
    }


   //http://localhost:8080/api/v1/employee?pageNo=5&pageSize=4&sortBy=emailId&sortDir=asc
    @GetMapping
    public ResponseEntity<List<EmployeeDto> >  getAllEmployees(
            @RequestParam(name = "pageSize",required = false,defaultValue ="5" )int pageSize,
            @RequestParam(name = "pageNo",required = false,defaultValue ="0" )int pageNo,
            @RequestParam(name="sortBy" , required = false,defaultValue ="name")String sortBy,
            @RequestParam(name="sortDir" , required = false,defaultValue ="asc")String sortDir
    ){
        List<EmployeeDto>  employeeDto = employeeService.getAllEmployees(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);

    }

    //http://localhost:8080/api/v1/employee/employeeId/{empId}
    //{empId} this is Pathvariable query

    @GetMapping("/employeeId/{empId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(
            @PathVariable Long empId
    ){
        EmployeeDto dto= employeeService.getEmployeeById(empId);

        return new ResponseEntity<>(dto,HttpStatus.OK);



    }





}
