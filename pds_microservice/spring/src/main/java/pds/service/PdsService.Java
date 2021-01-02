package pds;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/pds")
public class PdsService {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> index() {

        return ResponseEntity.noContent().build();
    }
}

