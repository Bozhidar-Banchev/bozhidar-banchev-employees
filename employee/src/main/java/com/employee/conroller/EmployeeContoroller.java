/**
 *
 */
package com.employee.conroller;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;

import com.employee.dto.EmployeeDto;
import com.employee.service.EmployeeService;

/**
 * @author Bozhidar
 *
 */
@Component
@Path("/employee")
public class EmployeeContoroller {

    private EmployeeService employeeService;
    
    @Inject
    public EmployeeContoroller(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @POST
    @Path("/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@FormDataParam("file") InputStream fileInputStream) {
        List<EmployeeDto> response = employeeService.findEmployeePairs(fileInputStream);
        return Response.ok(response).build();
    }
}
