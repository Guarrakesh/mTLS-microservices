package prenotazioni.fixtures;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prenotazioni.entity.Prenotazione;
import prenotazioni.repository.PrenotazioniRepository;

import java.io.File;


@Configuration
public class PreloadPrenotazioni {

    private static final Logger log = LoggerFactory.getLogger(PreloadPrenotazioni.class);


    @Bean
    CommandLineRunner initDatabase(PrenotazioniRepository repo) {

        return args -> {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();

            Prenotazione[] prenotazioni = mapper.readValue(new File("/app/data/fixtures/prenotazioni.yml"), Prenotazione[].class);

            for (Prenotazione prenotazione: prenotazioni) {
                log.info(prenotazione.getAppId() + " : " + prenotazione.getStudMatr());
                log.info("Preloading " + repo.save(new Prenotazione(prenotazione.getId(), prenotazione.getAttDidId(), prenotazione.getCdsId(), prenotazione.getAppId(), prenotazione.getStudId(), prenotazione.getStudMatr(), prenotazione.getDataCreazione())));
            }
        };
    }

}
