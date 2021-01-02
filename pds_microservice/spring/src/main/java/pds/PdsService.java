package pds;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/pds")
public class PdsService {


    private final PdsRepository repository;

    PdsService(PdsRepository repo) {
        this.repository = repo;
    }
    @RequestMapping(method = RequestMethod.GET)
    public List<PercorsoDiStudi> index() {

        return repository.findAll();
    }
}

