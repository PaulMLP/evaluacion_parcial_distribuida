package com.distribuida.cliente;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.consul.CheckOptions;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.consul.ConsulClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ClienteLifeCycle {
    @ConfigProperty(name = "quarkus.http.port")
    private int port;

    @ConfigProperty(name = "consul.host", defaultValue = "localhost")
    private String consultHost;

    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    private int consultPort;

    private String serviceId;

    public void init(@Observes StartupEvent evt, Vertx vertx) throws UnknownHostException {
        System.out.println("***ClienteLifeCycle Init***");

        ConsulClient client = ConsulClient.create(vertx,
                new ConsulClientOptions().setHost(consultHost).setPort(consultPort)
        );

        serviceId = UUID.randomUUID().toString();
        var ipAddress = InetAddress.getLocalHost();
        String httpCheckUrl = String.format("http://%s:%d/q/health/live", ipAddress.getHostAddress(), port);

        client.registerServiceAndAwait(
                new ServiceOptions()
                        .setName("app-clientes")
                        .setId(serviceId)
                        .setAddress(ipAddress.getHostAddress())
                        .setPort(port)
                        .setTags(
                                List.of("traefik.enable=true",
                                        "traefik.http.routers.app-clientes.rule=PathPrefix(`/app-clientes`)",
                                        "traefik.http.routers.app-clientes.middlewares=app-clientes",
                                        "traefik.http.middlewares.app-clientes.stripPrefix.prefixes=/app-clientes"
                                )
                        )
                        .setCheckOptions(
                                new CheckOptions()
                                        .setHttp(httpCheckUrl)
                                        .setInterval("10s")
                                        .setDeregisterAfter("20s")
                        )
        );
    }

    public void stop(@Observes ShutdownEvent stevt, Vertx vertx) {
        System.out.println("***ClienteLifeCycle Stop***");

        ConsulClient client = ConsulClient.create(vertx,
                new ConsulClientOptions().setHost(consultHost).setPort(consultPort)
        );

        client.deregisterServiceAndAwait(serviceId);
    }
}