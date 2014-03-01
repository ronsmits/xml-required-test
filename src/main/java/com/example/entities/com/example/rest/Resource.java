package com.example.entities.com.example.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;

/**
 * Created by ron on 3/1/14.
 */
@Path("/jackson")
public class Resource {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Object sayHelloJson() {
        return Arrays.asList(new String[]{"Peter", "pan", "Ihihi"});
    }
}