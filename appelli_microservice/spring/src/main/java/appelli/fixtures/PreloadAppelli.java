package appelli.fixtures;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import appelli.entity.Appello;
import appelli.repository.AppelliRepository;

import java.io.File;

@Configuration
public class PreloadAppelli {

    private static final Logger log = LoggerFactory.getLogger(PreloadAppelli.class);


    @Bean
    CommandLineRunner initDatabase(AppelliRepository repo) {

        return args -> {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();

            Appello[] appelli = mapper.readValue(new File("/app/data/fixtures/appelli.yml"), Appello[].class);

            for (Appello appello: appelli) {
                log.info(appello.getAttDidId() + " : " + appello.getAttDidName());
                log.info("Preloading " + repo.save(new Appello(appello.getId(), appello.getAttDidId(), appello.getAttDidName(), appello.getCdsId(), appello.getCdsName())));
            }
        };
    }

}




