package com.programacion.distribuida.authors.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;

@Path("/config")
public class TestConfiguracion {

//    @Inject con quarkus la propiedad no es necesaria
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    Integer port;

    @GET
    public String test(){
        Config config =  ConfigProvider.getConfig();

//        Lista de fuentes de configuracion

        config.getConfigSources().forEach(cs -> {
            System.out.printf("[%d] \t %s\n ", cs.getOrdinal(), cs.getName());
        });

//        recuperar un calor desde la configuracion
        String url = config.getValue("quarkus.datasource.jdbc.url",String.class);
        Integer puerto = config.getValue("quarkus.http.port",Integer.class);
        Optional<String> title = config.getOptionalValue("app.title",String.class);

        System.out.println("-----------------------------");
        System.out.println("URL: " + url);
        System.out.println("Puerto: " + puerto);

        if (title.isPresent()){
            System.out.println("Titile: " + title.get());
        }else {
            System.out.println("Titile no encontrado");
        }


        System.out.println("Puerto (DI): " + port);


        return "OK";
    }

}
