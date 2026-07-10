package com.programacion.distribuida.customers;

import io.quarkus.runtime.Shutdown;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.CheckOptions;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.util.List;

@ApplicationScoped
public class CustomersLifeCycle {

    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "127.0.0.1")
    String consulHost;

    @Inject
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consulPort;

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8090")
    Integer appPort;

    public void init(@Observes StartupEvent event, Vertx vertx) {
        try {
            System.out.println("CustomersLifeCycle init");

            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            ConsulClient client = ConsulClient.create(vertx, options);

            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            String serviceId = "app-customers%s:%d".formatted(ipAddress, appPort);

            var urlCheck = "http://%s:%d/ping".formatted(ipAddress, appPort);
            CheckOptions checkOptions = new CheckOptions()
                    .setHttp(urlCheck)
                    .setInterval("10s")
                    .setDeregisterAfter("10s");

            var tags = List.of(
                    "traefik.enable=true",
                    "traefik.http.routers.router-app-customers.rule=PathPrefix(`/app-customers`)",
                    "traefik.http.routers.router-app-customers.middlewares=middleware-customers",
                    "traefik.http.middlewares.middleware-customers.stripprefix.prefixes=/app-customers"
            );

            ServiceOptions serviceOptions = new ServiceOptions()
                    .setName("app-customers")
                    .setId(serviceId)
                    .setAddress(ipAddress)
                    .setPort(appPort)
                    .setCheckOptions(checkOptions)
                    .setTags(tags);

            client.registerService(serviceOptions)
                    .onSuccess(it -> System.out.println("Customers service registered in consul with ID: " + serviceId))
                    .onFailure(err -> System.out.println("Failed to register customers service in consul: " + err.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy(@Observes Shutdown event, Vertx vertx) {
        try {
            System.out.println("CustomersLifeCycle destroy");

            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            ConsulClient client = ConsulClient.create(vertx, options);

            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            String serviceId = "app-customers%s:%d".formatted(ipAddress, appPort);

            client.deregisterService(serviceId)
                    .onSuccess(it -> System.out.println("Customers service deregistered from Consul with ID: " + serviceId))
                    .onFailure(err -> System.out.println("Failed to deregister customers service from Consul: " + err.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
