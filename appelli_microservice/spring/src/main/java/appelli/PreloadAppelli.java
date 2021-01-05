package appelli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PreloadAppelli {

    private static final Logger log = LoggerFactory.getLogger(PreloadAppelli.class);

    @Bean
    CommandLineRunner initDatabase(AppelliRepository repo) {
        return args -> {
                log.info("Preloading " + repo.save(new Appello("AFC - AZIENDALE", "LM19AAC")));
                log.info("Preloading " + repo.save(new Appello("Ingegneria Informatica", "LM19IINF")));
        };
    }

}
