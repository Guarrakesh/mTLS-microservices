package registrazioni;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class Config {

    private Map<String, Object> network;

    private Map<String, Object> server;

    private Map<String, Object> spring;

    // standard getters and setters

    public Map<String,Object> getNetwork() {
        return network;
    }

    public void setNetwork(Map<String, Object> network) {
        this.network = network;
    }

    public Map<String, Object> getServer() {
        return server;
    }

    public void setServer(Map<String, Object> server) {
        this.server = server;
    }

    public Map<String, Object> getSpring() {
        return spring;
    }

    public void setSpring(Map<String, Object> spring) {
        this.spring = spring;
    }
}