package com.lcqjoyce.service;

import com.lcqjoyce.entity.Employee;

import java.util.List;

/**
 * @author ：LCQJOYCE
 * @date ：Created in 2020/3/15 18:31
 * @description：
 * @version: $
 */
public interface EmployeeService {

    List<Employee> getAllEmployees();

    List<Employee> getEmployeesPerPage(int currentPage, int size);

    int addEmployee(Employee emp);

    Employee getEmployeeByNo(String empNo);

    int updateEmployee(Employee emp);

    List<Employee> getQueryEmployees(String empName, String empDo);

    int deleteEmployee(Employee emp);
}
