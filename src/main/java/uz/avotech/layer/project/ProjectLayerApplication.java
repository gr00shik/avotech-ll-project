package uz.avotech.layer.project;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAdminServer
public class ProjectLayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectLayerApplication.class, args);
    }

}
