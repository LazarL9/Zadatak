import java.util.List;
import java.util.Objects;

public class Osoba {

private String ime;
private List<Osoba> djeca;

    public Osoba(String ime, List<Osoba> djeca) {
        this.ime = ime;
        this.djeca = djeca;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public List<Osoba> getDjeca() {
        return djeca;
    }

    public void setDjeca(List<Osoba> djeca) {
        this.djeca = djeca;
    }

    @Override
    public String toString() {
        return "Osoba{" +
                "Ime='" + ime + '\'' +
                ", djeca=" + djeca +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Osoba osoba = (Osoba) o;
        return Objects.equals(ime, osoba.ime) && Objects.equals(djeca, osoba.djeca);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ime, djeca);
    }
}
