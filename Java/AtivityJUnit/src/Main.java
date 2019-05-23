package activityjunit;

import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author André Spindola <decospdl@gmail.com>
 * @author Eduardo Kegler
 */
public class Main {

    public static void main(String[] args) {
        Team t1 = new Team("Teste");
        Team t2 = new Team("Maluco");

        Goalkeeper gk1 = new Goalkeeper(LocalDate.of(1980, Month.MARCH, 11), 13, 22, 55);
        Attacker at1 = new Attacker(LocalDate.of(1980, Month.MARCH, 11), 13, 22, 55);
        Attacker at2 = new Attacker(LocalDate.of(1980, Month.MARCH, 11), 13, 22, 55);
        Attacker at3 = new Attacker(LocalDate.of(1980, Month.MARCH, 11), 13, 22, 55);
        Attacker at4 = new Attacker(LocalDate.of(1980, Month.MARCH, 11), 13, 22, 55);
        Goalkeeper gk2 = new Goalkeeper(LocalDate.of(1980, Month.MARCH, 11), 13, 32, 55);
        Defender d1 = new Defender(LocalDate.of(1980, Month.MARCH, 11), 13, 22, 55);
        Defender d2 = new Defender(LocalDate.of(1980, Month.MARCH, 11), 13, 22, 55);
        Defender d3 = new Defender(LocalDate.of(1980, Month.MARCH, 11), 13, 22, 55);
        Defender d4 = new Defender(LocalDate.of(1980, Month.MARCH, 11), 13, 22, 55);

        t1.addPlayer(gk1);
        t1.addPlayer(at1);
        t1.addPlayer(at2);
        t1.addPlayer(at3);
        t1.addPlayer(at4);

        t2.addPlayer(gk2);
        t2.addPlayer(d1);
        t2.addPlayer(d2);
        t2.addPlayer(d3);
        t2.addPlayer(d4);

        Match m = new Match(t1, t2, LocalDate.now());

        m.addGols(gk2);
        m.addGols(gk2);
        m.addGols(gk1);

        m.showGols();
        m.showChance();
        m.finshMatch();

        t2.showResults();
        t1.showResults();
    }

}
