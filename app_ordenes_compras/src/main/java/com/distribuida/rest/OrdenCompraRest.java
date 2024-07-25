package com.distribuida.rest;

import com.distribuida.clients.ClienteRestClient;
import com.distribuida.clients.ProductoRestClient;
import com.distribuida.db.OrdenCompra;
import com.distribuida.dtos.OrdenCompraDTO;
import com.distribuida.repo.OrdenCompraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;


@Path("/ordenes-compras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ApplicationScoped
public class OrdenCompraRest {

    @Inject
    OrdenCompraRepository repo;

    @Inject
    @RestClient
    ClienteRestClient cleinteRest;

    @Inject
    @RestClient
    ProductoRestClient productoRest;

    //Listar todos
    @GET
    public List<OrdenCompraDTO> findAll() {
        try {
            var ordenes = repo.listAll();
            return ordenes.stream()
                    .map(orden -> {
                        var cliente = cleinteRest.findById(orden.getClienteId());
                        var producto = productoRest.findById(orden.getProductoId());

                        var dto = new OrdenCompraDTO();
                        dto.setId(orden.getId());
                        dto.setClienteId(cliente.getId());
                        dto.setNombreCliente(cliente.getNombre());
                        dto.setProductoId(producto.getId());
                        dto.setNombreProducto(producto.getNombre());
                        dto.setPrecio(orden.getPrecio());

                        return dto;
                    })
                    .toList();
        }catch (Error e){
            return null;
        }

    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {
        var op = repo.findByIdOptional(id);
        if (op.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(op.get()).build();
    }

    @POST
    public Response create(OrdenCompra oc) {
        oc.setId(null);
        repo.persist(oc);
        return Response.status(Response.Status.CREATED).build();
    }


    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, OrdenCompra oc) {
        var obj = repo.findById(id);

        obj.setClienteId(oc.getClienteId());
        obj.setProductoId(oc.getProductoId());
        obj.setPrecio(oc.getPrecio());

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        repo.deleteById(id);
        return Response.ok().build();
    }
}

