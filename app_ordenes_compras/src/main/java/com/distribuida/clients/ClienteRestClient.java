package com.distribuida.clients;

import com.distribuida.dtos.ClienteDTO;
import jakarta.ws.rs.*;
        import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "stork://app-clientes")
public interface ClienteRestClient {
    @GET
    @Path("/{id}")
    ClienteDTO findById(@PathParam("id") Integer id);
}