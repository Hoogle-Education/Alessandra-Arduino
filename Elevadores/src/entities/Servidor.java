package entities;
import java.util.ArrayList;
import java.util.List;

// gerador de servidores com uma lista de pessoas
public class Servidor {

    List<Pessoa> fila;

    public Servidor() {
        fila = new ArrayList<>();
    }

    // método add adiciona a pessoa ao final da fila
    public void add(Pessoa pessoa) {
        fila.add(pessoa);
    }

    // remove remove o primeiro se houver
    public void remove() {
        if (!fila.isEmpty())
            fila.remove(0);
    }

    // retorna a pessoa da frente, se ela existir
    public Pessoa frente() {
        if (!fila.isEmpty())
            return fila.get(0);
        else
            return null;
    }

    @Override
    public String toString() {
        String aux = "Andar: ";

        for (Pessoa pessoa : fila)
            aux += pessoa.getNome() + ", ";

        return aux + "\n";
    }

    public List<Pessoa> getFila() {
        return fila;
    }

    public void setFila(List<Pessoa> fila) {
        this.fila = fila;
    }
}