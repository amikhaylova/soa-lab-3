package com.soa.controller;

import com.soa.enums.MovieGenre;
import com.soa.service.OscarService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Hashtable;

@Path("oscar")
public class OscarController extends Application {

    private OscarService oscarService;

    public OscarController() {
        oscarService = lookupRemoteOscarService();
    }

    @GET
    public Response ping() {
        return Response.ok().entity(oscarService.ping()).build();
    }

    @GET
    @Path("genres/redistribute-rewards/{from-genre}/{to-genre}")
    public Response reallocateOscars(@PathParam("from-genre") MovieGenre fromGenre,
                                     @PathParam("to-genre") MovieGenre toGenre) {
        oscarService.reallocateOscars(fromGenre, toGenre);
        return Response.ok().build();
    }

    @GET
    @Path("movies/honor-by-length/{min-length: [1-9]\\d*}/{oscars-to-add: [1-9]\\d*}")
    public Response increaseOscarsWhereDurationIsGreater(@PathParam("min-length") Long duration,
                                                         @PathParam("oscars-to-add") Integer oscars) {
        oscarService.increaseOscars(duration, oscars);
        return Response.ok().build();
    }

    private OscarService lookupRemoteOscarService() {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            final Context context = new InitialContext(jndiProperties);
            String workspace = "java:global";
            String moduleName = "soa-lab3-ejb";
            String ejbName = "OscarServiceImpl";
            return (OscarService) context.lookup(workspace + "/" + moduleName + "/" + ejbName);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
