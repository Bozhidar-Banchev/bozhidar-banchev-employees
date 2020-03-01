/**
 *
 */
package com.employee.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import com.employee.dto.EmployeeDto;

/**
 * @author Bozhidar
 *
 */
@Service
@Transactional
public class EmployeeService {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Find pair of employees worked for the longest time together in one
     * project
     *
     * @param fileInputStream
     * @throws ClassNotFoundException
     */
    public List<EmployeeDto> findEmployeePairs(InputStream inputStream) {
        List<EmployeeDto> employeDtos = readFile(inputStream);
        setWorkProjectDurationTime(employeDtos);
        List<EmployeeDto> response = findEmployeesWorkedLongestTime(employeDtos);
        response.stream().forEach(e -> {
            System.out.printf("Employee ID: %s, Project ID: %s, Days worked: %s \n", e.getId(), e.getProjectId(),
                    e.getWorkedDaysInProject());
        });
        return response;
    }

    /**
     * Find employees worked for the longest time together in one project
     *
     * @param employeDtos
     * @return
     */
    private List<EmployeeDto> findEmployeesWorkedLongestTime(List<EmployeeDto> employeDtos) {
        List<EmployeeDto> response = new ArrayList<>();
        long sumWorkedTimeTogether = 0L;
        EmployeeDto firstEmployee = new EmployeeDto();
        EmployeeDto secondEmployee = new EmployeeDto();
        for (int i = 0; i < (employeDtos.size() - 1); i++) {
            EmployeeDto employeeDto = employeDtos.get(i);
            for (int j = i + 1; j < employeDtos.size(); j++) {
                EmployeeDto dto = employeDtos.get(j);
                if (employeeDto.getProjectId().equals(dto.getProjectId())) {
                    long sumWorkDays = employeeDto.getWorkedDaysInProject() + dto.getWorkedDaysInProject();
                    if (sumWorkDays > sumWorkedTimeTogether) {
                        sumWorkedTimeTogether = sumWorkDays;
                        firstEmployee = employeeDto;
                        secondEmployee = dto;
                    }
                }
            }
        }
        response.add(firstEmployee);
        response.add(secondEmployee);
        return response;
    }

    /**
     * Set to each emploee working days on a project
     *
     * @param employeDtos
     */
    private void setWorkProjectDurationTime(List<EmployeeDto> employeDtos) {
        for (EmployeeDto employeeDto : employeDtos) {
            logger.info("___INFO___Set emploee working days on a project");
            long days = ChronoUnit.DAYS.between(employeeDto.getDateFrom(), employeeDto.getDateTo());
            employeeDto.setWorkedDaysInProject(days);
        }
    }

    /**
     * Read input file and transfer data to Objects
     *
     * @param inputStream
     * @return
     */
    private List<EmployeeDto> readFile(InputStream inputStream) {
        List<EmployeeDto> employeeDtos = new ArrayList();
        try {
            logger.info("___Start reading file.");
            String inputData = IOUtils.toString(inputStream);
            String[] splitDataByRow = inputData.split("\n");
            for (String row : splitDataByRow) {
                String[] rowData = Arrays.stream(row.split(",")).map(String::trim).toArray(String[]::new);
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setId(Long.valueOf(rowData[0]));
                employeeDto.setProjectId(Long.valueOf(rowData[1]));
                employeeDto.setDateFrom(LocalDate.parse(rowData[2], DateTimeFormatter.ISO_DATE));
                employeeDto.setDateTo((rowData[3].isEmpty() || rowData[3].equals("NULL")) ? LocalDate.now()
                        : LocalDate.parse(rowData[3], DateTimeFormatter.ISO_DATE));
                employeeDtos.add(employeeDto);
            }
        } catch (IOException e) {
            logger.error("___Error___while reading data from file: {}", e.getMessage());
        }
        return employeeDtos;
    }

}
