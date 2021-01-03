package prenotazioni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;


@Configurable
@RestController
@RequestMapping(value = "/prenotazioni")
public class PrenotazioniService {

    @Autowired
    Config configuration;


    @RequestMapping(method = RequestMethod.GET)
    public Object index() {
        try {
            Map<String, String> pdsHost = (Map<String, String>) configuration.getNetwork().get("pds");
            URI uri = URI.create(pdsHost.get("host"));
            RestTemplate restTemplate = new RestTemplate();
            Object pds = restTemplate.getForEntity(uri + "/pds", Object.class);

            return pds;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "started";
    }
}

