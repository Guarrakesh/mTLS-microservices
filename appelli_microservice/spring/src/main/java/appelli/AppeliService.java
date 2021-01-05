package appelli;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/pds")
public class AppeliService {


    private final AppelliRepository repository;

    AppeliService(AppelliRepository repo) {
        this.repository = repo;
    }
    @RequestMapping(method = RequestMethod.GET)
    public List<Appello> index() {

        return repository.findAll();
    }
}

