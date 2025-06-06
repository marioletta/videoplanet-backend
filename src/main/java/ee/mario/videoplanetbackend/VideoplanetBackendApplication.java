package ee.mario.videoplanetbackend;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class VideoplanetBackendApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(VideoplanetBackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Welcome to Videoplanet!");
    }


}
