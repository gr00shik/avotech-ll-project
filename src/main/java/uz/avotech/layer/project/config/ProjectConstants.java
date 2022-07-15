package uz.avotech.layer.project.config;

import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

@Configuration
public class ProjectConstants {
    public static final String PROJECT_NAME = "avotech-ll-project";
    public static final String ROOT_PATH;

    static {
        ROOT_PATH = stream(ProjectConstants.class.getPackageName()
                .split("\\."))
                .limit(4)
                .collect(joining("."));
    }
}
