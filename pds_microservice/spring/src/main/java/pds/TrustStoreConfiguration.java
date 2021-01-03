
package pds;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class TrustStoreConfiguration implements EnvironmentAware {

    @Override
    public void setEnvironment(final Environment environment) {
        String truststoreLocation = environment.getProperty("server.ssl.trust-store");
        String truststorePassword = environment.getProperty("server.ssl.trust-store-password");

        if (truststoreLocation != null && truststorePassword != null) {
			System.setProperty("javax.net.ssl.trustStore", truststoreLocation);
			System.setProperty("javax.net.ssl.trustStorePassword", truststorePassword);
        }


        String keyStoreLocation = environment.getProperty("server.ssl.key-store");
        String keyStorePassword = environment.getProperty("server.ssl.key-store-password");
        if (keyStoreLocation != null && keyStorePassword != null) {
            System.setProperty("javax.net.ssl.keyStore", keyStoreLocation);
            System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
        }
    }
}