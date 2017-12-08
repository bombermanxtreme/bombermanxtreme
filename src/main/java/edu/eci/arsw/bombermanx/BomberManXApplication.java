package edu.eci.arsw.bombermanx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.bombermanx"})
public class BomberManXApplication {

    public static void main(String[] args) {
        SpringApplication.run(BomberManXApplication.class, args);
    }
}
