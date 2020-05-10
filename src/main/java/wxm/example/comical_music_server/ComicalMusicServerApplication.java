package wxm.example.comical_music_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ComicalMusicServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComicalMusicServerApplication.class, args);
    }

}
