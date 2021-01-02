package prenotazioni;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "network")
@EnableConfigurationProperties
public class NetworkProperties {

    private Object pds;

    public Object getPds() {
        return pds;
    }
}
