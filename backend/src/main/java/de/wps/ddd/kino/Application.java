package de.wps.ddd.kino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration;

@SpringBootApplication
@ImportAutoConfiguration(exclude = WebServiceTemplateAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
