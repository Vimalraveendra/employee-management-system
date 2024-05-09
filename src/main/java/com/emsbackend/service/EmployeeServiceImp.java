package com.emsbackend.service;

import com.emsbackend.dto.EmployeeDto;
import com.emsbackend.entity.Employee;
import com.emsbackend.exception.EmployeeNotFoundException;
import com.emsbackend.mapper.EmployeeMapper;
import com.emsbackend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImp implements EmployeeService{

    private EmployeeRepository employeeRepository; // created constructor dependency using @AllArgsConstructor
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto){
        Employee employee = EmployeeMapper.convertEmployeeDtoToEmployee(employeeDto);
        Employee savedEmployee =employeeRepository.save(employee);
        return EmployeeMapper.convertEmployeeToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee= employeeRepository.findById(employeeId).orElseThrow(()->new EmployeeNotFoundException(employeeId));
        return EmployeeMapper.convertEmployeeToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees=  employeeRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
        return employees.stream().
                map((employee -> EmployeeMapper.convertEmployeeToEmployeeDto(employee)))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        Employee employee= employeeRepository.findById(employeeId).orElseThrow(()->new EmployeeNotFoundException(employeeId));
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        Employee updatedEmployee=employeeRepository.save(employee);
        return EmployeeMapper.convertEmployeeToEmployeeDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee =employeeRepository.findById(employeeId).orElseThrow(()->new EmployeeNotFoundException(employeeId));
        employeeRepository.deleteById(employee.getId());
    }
}
