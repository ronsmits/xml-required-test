package com.example.entities.com.example.rest;

import com.example.entities.Catalog;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by ron on 3/1/14.
 */
@Path("/catalogs")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogResource {

    @GET
    public Catalog getCatalog(){
        Catalog catalog = fillCatalog();

        return catalog;
    }

    @GET
    @Path("filledChild")
    public Catalog getCatalogWithFilledChildren(){
        Catalog catalog = fillCatalog();
        catalog.addChild(new Catalog("1.1", "child 1"));
        return catalog;
    }

    @GET
    @Path("twoChildren")
    public Catalog getCatalogWith2Children () {
        Catalog catalog = getCatalogWithFilledChildren();
        catalog.addChild(new Catalog("1.2", "child 2"));
        return catalog;
    }

    private Catalog fillCatalog() {
        Catalog cat = new Catalog("1", "root");
        return cat;
    }
}
