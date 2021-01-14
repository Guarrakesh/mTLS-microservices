package appelli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import appelli.entity.Appello;
import appelli.repository.AppelliRepository;
import java.util.List;


@RestController
@RequestMapping(value = "/appelli")
public class AppeliService {

    @Autowired
    Config configuration;

    @Autowired
    AppelliRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Appello>> index() {
        try {

            List<Appello> appelli = repository.findAll();

            return ResponseEntity.ok(appelli);
        } catch (Exception e){
            e.printStackTrace();
        }

        return  ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Appello body) {
        try {
            return ResponseEntity.ok(repository.save(new Appello(body)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.noContent().build();
    }

}

