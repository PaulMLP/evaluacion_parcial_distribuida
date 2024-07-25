package com.distribuida.producto.rest;

import com.distribuida.producto.db.Producto;
import com.distribuida.producto.repo.ProductoRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class ProductoRest {

    @Inject
    ProductoRepository rep;

    @GET
    public List<Producto> findAll() {
        return rep.findAll().list();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {
        var producto = rep.findByIdOptional(id);
        if (producto.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(producto.get()).build();
    }

    @POST
    public Response create(Producto p) {
        rep.persist(p);
        return Response.status(Response.Status.CREATED.getStatusCode(), "producto creado").build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Producto productoObj) {
        Producto producto = rep.findById(id);
        producto.setNombre(productoObj.getNombre());
        producto.setPrecio(productoObj.getPrecio());
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        rep.deleteById(id);
        return Response.ok().build();
    }
}

