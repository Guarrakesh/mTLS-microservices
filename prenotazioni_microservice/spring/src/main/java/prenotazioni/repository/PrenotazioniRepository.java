package prenotazioni.repository;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PrenotazioniRepository extends JpaRepository<prenotazioni.entity.Prenotazione, Long> {

}
