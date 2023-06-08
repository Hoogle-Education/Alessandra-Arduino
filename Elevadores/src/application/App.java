package application;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import entities.Pessoa;
import entities.Servidor;
import utilities.GeneratedRandomPessoa;

public class App {

    private static long tempoInicial = System.currentTimeMillis();
    private static BigDecimal tempoMaximoEsperando = new BigDecimal("-1"); // o maximo absurdo
    private static List<BigDecimal> temposEsperando = new ArrayList<>();
    private static int numeroDePessoas = 0;

    // constantes do problema
    private static final int NUMERO_DE_ELEVADORES = 2;
    private static final int QUANTIDADE_DE_INTERACOES = 10;

    public static BigDecimal tempoAtual() {
        return BigDecimal.valueOf( System.currentTimeMillis() - tempoInicial );
    }

    public static void main(String[] args) throws Exception {

        Servidor[] servidores = new Servidor[6];
        for (int i = 1; i <= 5; i++)
            servidores[i] = new Servidor();

        int contador = 0;

        while (contador <= QUANTIDADE_DE_INTERACOES) {

            System.out.println("--------------------------");
            System.out.println("CONTADOR = " + contador++ );

            for (int i = 1; i <= 5; i++) {
                System.out.print("#" + i + " " + servidores[i]);
            }

            System.out.println("--------------------------");

            Thread.sleep(1000);

            insereNaFila(servidores);

            for(int i=0; i<NUMERO_DE_ELEVADORES; i++){
                liberaElevador(servidores);
            }

        }

        System.out.println("");
        System.out.println("#########################");
        apresetaEstatisticas();
        System.out.println("#########################");
        System.out.println("");
    }

    private static void insereNaFila(Servidor[] servidores) throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            Pessoa pessoa = GeneratedRandomPessoa.get();
            pessoa.setMomentoEntrada(BigDecimal.valueOf(System.currentTimeMillis()));
            servidores[i].add(pessoa);

            System.out.println("[tempo:" 
                        + tempoAtual().divide(new BigDecimal("1000")) 
                        + "] Pessoa " 
                        + i 
                        + " entrou na fila");
            Thread.sleep(300);
        }
    }

    private static void liberaElevador(Servidor[] servidores) throws InterruptedException {
        for (int i = 1; i <= 5; i++) {

            Pessoa frente = servidores[i].frente();

            if (frente == null)
                continue;

            if (tempoAtual().compareTo(frente.getTempoNecessario()) > 0) {
                
                System.out.println("[tempo:" 
                                    + tempoAtual().divide(new BigDecimal("1000")) 
                                    + "] o Cliente " 
                                    + i 
                                    + " esta saindo! tempo esperando = " 
                                    + frente.getTempoGasto());

                // descobre o maior tempo que houve para alguem sair das filas
                tempoMaximoEsperando = (frente.getTempoNecessario().compareTo(tempoMaximoEsperando) > 0) ? frente.getTempoNecessario() : tempoMaximoEsperando;
                
                // acumula o tempo necessário
                temposEsperando.add( frente.getTempoNecessario() );
                numeroDePessoas++;
                servidores[i].remove();
                Thread.sleep(300);
            }
        }

    }

    // calcula o desvio padrao
    private static BigDecimal desvioPadrao(List<BigDecimal> tempos) {
        
        BigDecimal somaDosDesvios = new BigDecimal("0");
        BigDecimal tempoMedio = tempoMedio(tempos);

        for(BigDecimal tempo : tempos) 
            somaDosDesvios.add( tempoMedio.add(tempo.negate()) );
        
        return (tempoMedio
                .divide( BigDecimal.valueOf(numeroDePessoas), 4, RoundingMode.HALF_DOWN))
                .sqrt(new MathContext(4));
    }

    private static BigDecimal tempoMedio(List<BigDecimal> tempos) {

        BigDecimal soma = new BigDecimal("0");

        for(BigDecimal tempo : tempos) 
            soma = soma.add(tempo);

        return soma
                .divide(BigDecimal.valueOf(numeroDePessoas), 4, RoundingMode.HALF_DOWN);
    }

    // produz as estatiscas do problema
    private static void apresetaEstatisticas() {

        System.out.println("QUANTIDADE DE ELEVADORES = " + NUMERO_DE_ELEVADORES);
        System.out.println("QUANTIDADE DE INTERACOES DO ELEVADOR = " + QUANTIDADE_DE_INTERACOES);
        System.out.println("TEMPO MAXIMO ESPERANDO = " + tempoMaximoEsperando );
        System.out.println("TEMPO MEDIO ESPERANDO = " + tempoMedio(temposEsperando) );
        System.out.println("DESVIO PADRAO = " + desvioPadrao(temposEsperando));

    }

}
