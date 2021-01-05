package appelli.repository;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AppelliRepository extends JpaRepository<appelli.entity.Appello, Long> {

}
