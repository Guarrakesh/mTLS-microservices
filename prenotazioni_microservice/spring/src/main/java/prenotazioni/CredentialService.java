package prenotazioni;

import prenotazioni.entity.Prenotazione;
import prenotazioni.fixtures.PreloadPrenotazioni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

class JWTDto {
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}


@RestController
@RequestMapping(value = "/credential")
public class CredentialService {

    private static final Logger log = LoggerFactory.getLogger(PreloadPrenotazioni.class);

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<List<Prenotazione>> storeJWT(@RequestBody JWTDto body ) {
        try {
            log.info("Received token : " + body.getJwt());


            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.noContent().build();
    }
}
