package com.distribuida.clients;

import com.distribuida.dtos.ProductoDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "stork://app-productos")
public interface ProductoRestClient {
    @GET
    @Path("/{id}")
    ProductoDTO findById(@PathParam("id") Integer id);
}