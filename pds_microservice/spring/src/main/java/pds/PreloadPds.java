package pds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PreloadPds {

    private static final Logger log = LoggerFactory.getLogger(PreloadPds.class);

    @Bean
    CommandLineRunner initDatabase(PdsRepository repo) {
        return args -> {
                log.info("Preloading " + repo.save(new PercorsoDiStudi("AFC - AZIENDALE", "LM19AAC")));
                log.info("Preloading " + repo.save(new PercorsoDiStudi("Ingegneria Informatica", "LM19IINF")));
        };
    }

}
