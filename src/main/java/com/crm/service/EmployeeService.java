package com.crm.service;


import com.crm.entity.Employee;
import com.crm.exception.ResourceNotFound;
import com.crm.payload.EmployeeDto;
import com.crm.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class EmployeeService {


    private EmployeeRepository employeeRepository;
    private ModelMapper modelmapper;

    public EmployeeService(EmployeeRepository employeeRepository,ModelMapper modelMapper){
        this.employeeRepository=employeeRepository;
        this.modelmapper=modelMapper;
    }

        //create add employee method for Employee entity
    public EmployeeDto addEmployee(EmployeeDto dto){
        //logic to save employee to database
       Employee employee= mapToEntity(dto);
        Employee emp= employeeRepository.save(employee);

       EmployeeDto employeeDto =mapToDto(emp);
        return employeeDto;

    }


    public void delteteEmployee(Long id) {
        employeeRepository.deleteById(id);

    }

   /* public void updateEmployee(Long id, EmployeeDto dto) {

        Optional<Employee> opemp = employeeRepository.findById(id);
        Employee employee = opemp.get();
        employee.setName(dto.getName());
        employee.setEmailId(dto.getEmailId());
        employee.setMobile(dto.getMobile());
        employeeRepository.save(employee);
        NOTE=  Here we update all data in direct in the employee entity class but in below code we update data in employeeDto class
        and we also return EmployeeDto object in the responce what i  update
    } */


    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
       Employee employee= mapToEntity(dto);
       employee.setId(id);
        Employee updateEmployee= employeeRepository.save(employee);

        return mapToDto(updateEmployee);

    }


    public List<EmployeeDto> getAllEmployees(int pageNo, int  pageSize, String sortBy, String sortDir) {

      Sort sort=  sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

       //Pageable page= PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Pageable page= PageRequest.of(pageNo, pageSize, sort);
        Page<Employee> all = employeeRepository.findAll(page);
        List<Employee>  employees = all.getContent();
        List<EmployeeDto> dto = employees.stream().map(e->mapToDto(e)).collect(Collectors.toList());
        return dto;

    }



    EmployeeDto mapToDto(Employee employee){
               EmployeeDto dto= modelmapper.map(employee,EmployeeDto.class);
               return dto;
//        EmployeeDto dto = new EmployeeDto();
//        dto.setId(employee.getId());
//        dto.setName(employee.getName());
//        dto.setEmailId(employee.getEmailId());
//        dto.setMobile(employee.getMobile());
//        return dto;


    }

    Employee mapToEntity(EmployeeDto dto){
        Employee emp= modelmapper.map(dto,Employee.class);
//        Employee emp= new Employee();
//        emp.setId(dto.getId());
//        emp.setName(dto.getName());
//        emp.setEmailId(dto.getEmailId());
//        emp.setMobile(dto.getMobile());

        return emp;

    }


    // orelseThrow is use here becUse if record is not found then throw this exception
    //()->  is supplier here it take not any input only produse output
    public EmployeeDto getEmployeeById(Long empId) {

        Employee employee=employeeRepository.findById(empId).orElseThrow(

                ()-> new ResourceNotFound("Resourc Not Found with id:"+ empId)
                //here i only throw the custom exception i does not handle it
        );
   EmployeeDto dto=mapToDto(employee);
        return dto;
    }
}
