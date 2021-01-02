package pds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import prenotazioni.NetworkProperties;

import java.net.URI;
import java.util.List;


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

