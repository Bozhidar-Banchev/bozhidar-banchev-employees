package com.employee.configuration;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.employee.conroller.EmployeeContoroller;

@Component
@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig {

    /**
     * register each controller in application path
     */
    public JerseyConfiguration() {
        this.register(EmployeeContoroller.class);
        this.register(MultiPartFeature.class);
    }
}
