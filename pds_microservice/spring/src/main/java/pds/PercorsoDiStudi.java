package pds;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PercorsoDiStudi {

    private @Id @GeneratedValue Long id;

    private String name;

    private String code;

    public PercorsoDiStudi(String name, String code) {
        this.name = name;
        this.code = code;
    }

    PercorsoDiStudi() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.code);
    }

    @Override
    public String toString() {
        return "Pds{" + "id=" + this.id + ", name='" + this.name + '\'' + ", code='" + this.code + '\'' + '}';
    }
}
