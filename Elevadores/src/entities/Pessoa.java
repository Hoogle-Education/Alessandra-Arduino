package entities;
import java.math.BigDecimal;
import java.util.Random;

public class Pessoa {

    private String nome; // nome gerado aleatoriamente
    private BigDecimal tempoNecessario; // tempo que sera gasto
    private BigDecimal momentoEntrada; // valor absoluto do tempo 

    private Random random = new Random();

    public Pessoa(String nome) {
        this.nome = nome;
        this.tempoNecessario = BigDecimal.valueOf( random.nextDouble(5000, 10000) );
    }

    public void setMomentoEntrada(BigDecimal momentoEntrada) {
        this.momentoEntrada = momentoEntrada;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getTempoNecessario() {
        return tempoNecessario;
    }

    public BigDecimal getMomentoEntrada() {
        return momentoEntrada;
    }

    public BigDecimal getMomentoSaida() {
        return momentoEntrada.add(tempoNecessario);
    }

    public BigDecimal getTempoGasto() {
        return ( getMomentoSaida().add(getMomentoEntrada().negate()) )
                .divide(new BigDecimal("1000.0"));
    }

}