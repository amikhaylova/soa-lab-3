package com.soa;

import com.soa.controller.OscarController;
import com.soa.exception.BadRequestMapper;
import com.soa.exception.ServerErrorMapper;
import com.soa.filter.CorsFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("")
public class CallerApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(CorsFilter.class);
        resources.add(OscarController.class);
        resources.add(ServerErrorMapper.class);
        resources.add(BadRequestMapper.class);
        return resources;
    }

}
