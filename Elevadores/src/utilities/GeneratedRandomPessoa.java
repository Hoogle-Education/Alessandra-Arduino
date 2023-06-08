package utilities;
import java.util.Random;

import entities.Pessoa;

// gerador de nomes aleatórios
public class GeneratedRandomPessoa {

    private static String[] nomes = {
            "Maria", "Pedro", "Matheus", "Carlos",
            "Beatriz", "Ana", "Jorge" };

    private static Random random = new Random();

    public static Pessoa get() {
        int pos = random.nextInt(7);
        return new Pessoa(nomes[pos]);
    }

}