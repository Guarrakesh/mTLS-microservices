package prenotazioni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import prenotazioni.entity.Prenotazione;
import prenotazioni.repository.PrenotazioniRepository;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/prenotazioni")
public class PrenotazioniService {

    @Autowired
    Config configuration;

    @Autowired
    PrenotazioniRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Prenotazione>> index(@Param("attDidId") String attDidId) {
        try {

            List<Prenotazione> prenotazioni = repository.findAll();

            if (attDidId != null) {
                prenotazioni = prenotazioni.stream().filter(prenotazione ->
                        prenotazione.getAttDidId().equals(attDidId)
                ).collect(Collectors.toList());
            }
            return ResponseEntity.ok(prenotazioni);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Prenotazione body) {
        try {
            return ResponseEntity.ok(repository.save(new Prenotazione(body)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.noContent().build();
    }
}
