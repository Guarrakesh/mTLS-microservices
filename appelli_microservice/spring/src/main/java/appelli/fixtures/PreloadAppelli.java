package appelli.fixtures;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import appelli.entity.Appello;
import appelli.repository.AppelliRepository;
import org.springframework.core.env.Environment;

import java.io.File;
import java.util.Map;

@Configuration
public class PreloadAppelli implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(PreloadAppelli.class);
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    CommandLineRunner initDatabase(AppelliRepository repo) {

        return args -> {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();


            Map<String, String> env = System.getenv();
            String dataPath = env.get("DATA_PATH");
            Appello[] appelli = mapper.readValue(new File(dataPath + "/fixtures/appelli.yml"), Appello[].class);

            for (Appello appello: appelli) {
                log.info(appello.getAttDidId() + " : " + appello.getAttDidName());
                log.info("Preloading " + repo.save(new Appello(appello)));
            }
        };
    }

}




