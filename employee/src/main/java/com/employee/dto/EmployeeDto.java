/**
 *
 */
package com.employee.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Bozhidar
 *
 */
public class EmployeeDto implements Serializable {

    private static final long serialVersionUID = -337756183074911916L;

    private Long      id;
    private Long      projectId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Long      workedDaysInProject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public Long getWorkedDaysInProject() {
        return workedDaysInProject;
    }

    public void setWorkedDaysInProject(Long workedDaysInProject) {
        this.workedDaysInProject = workedDaysInProject;
    }

}
