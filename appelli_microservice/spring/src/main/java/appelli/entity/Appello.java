package appelli.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Appello {
    private @Id @GeneratedValue Long id;

    private String attDidId;

    private String attDidName;

    private String cdsId;

    private String cdsName;

    private String dataAppello;

    private String aula;

    public Appello(){
    }

    public Appello(Appello a) {
        this.attDidId = a.attDidId;
        this.attDidName = a.attDidName;
        this.cdsId = a.cdsId;
        this.cdsName = a.cdsName;
        this.dataAppello = a.dataAppello;
        this.aula = a.aula;
    }

    public Appello(Long id, String attDidId, String attDidName, String cdsId, String cdsName, String dataAppello, String aula){
        this.attDidId = attDidId;
        this.attDidName = attDidName;
        this.cdsId = cdsId;
        this.cdsName = cdsName;
        this.aula = aula;
        this.dataAppello = dataAppello;
    }

    @Override
    public String toString() {
        return "Appello{" + "id=" + this.id + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.attDidId, this.attDidName, this.cdsId, this.cdsName);
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

    public String getAttDidName() {
        return attDidName;
    }

    public void setAttDidName(String attDidName) {
        this.attDidName = attDidName;
    }

    public String getCdsId() {
        return cdsId;
    }

    public void setCdsId(String cdsId) {
        this.cdsId = cdsId;
    }

    public String getCdsName() {
        return cdsName;
    }

    public void setCdsName(String cdsName) {
        this.cdsName = cdsName;
    }

    public String getDataAppello() {
        return dataAppello;
    }

    public void setDataAppello(String dataAppello) {
        this.dataAppello = dataAppello;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }
}
