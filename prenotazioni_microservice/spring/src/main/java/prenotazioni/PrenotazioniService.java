package prenotazioni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/prenotazioni")
public class PrenotazioniService {

    @Autowired
    NetworkProperties networkProperties;


    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return networkProperties.getPds().toString();
    }
}

