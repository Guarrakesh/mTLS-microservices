package prenotazioni.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Prenotazione {

    private @Id @GeneratedValue Long id;

    private String attDidId;

    private String cdsId;

    private String appId; // appello esame

    private String studId;

    private String studMatr;

    private String dataCreazione;

    public Prenotazione() {
    }

    public Prenotazione(Prenotazione p) {
        this.id = p.id;
        this.appId = p.appId;
        this.cdsId = p.cdsId;
        this.attDidId = p.attDidId;
        this.dataCreazione = p.dataCreazione;
        this.studId = p.studId;
        this.studMatr = p.studMatr;
    }

    public Prenotazione(Long id, String attDidId, String cdsId, String appId, String studId, String studMatr, String dataCreazione) {
        this.id = id;
        this.attDidId = attDidId;
        this.cdsId = cdsId;
        this.appId = appId;
        this.studId = studId;
        this.studMatr = studMatr;
        this.dataCreazione = dataCreazione;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.appId, this.studId);
    }

    @Override
    public String toString() {
        return "Prenotazione{" + "id=" + this.id + "}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttDidId() {
        return attDidId;
    }

    public void setAttDidId(String attDidId) {
        this.attDidId = attDidId;
    }

    public String getCdsId() {
        return cdsId;
    }

    public void setCdsId(String cdsId) {
        this.cdsId = cdsId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public String getStudMatr() {
        return studMatr;
    }

    public void setStudMatr(String studMatr) {
        this.studMatr = studMatr;
    }

    public String getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(String dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
}
