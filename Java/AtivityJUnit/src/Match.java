package activityjunit;

import java.time.LocalDate;

/**
 *
 * @author Administrator
 */
public class Match {

    private final static int HOME = 0, VISITOR = 1;

    private int[] gols = new int[2];
    private LocalDate dateMatch;
    private Team home, visitor;

    public Match(Team home, Team visitor, LocalDate dateMatch) {
        this.home = home;
        this.visitor = visitor;
        gols[HOME] = 0;
        gols[VISITOR] = 0;
        this.dateMatch = dateMatch;
        validateThereGoalKeeper(home);
        validateThereGoalKeeper(visitor);
        validateQtdTime();
    }

    private void validateQtdTime() {
        if (home.getPlayers().size() != 5 || visitor.getPlayers().size() != 5) {
            throw new RuntimeException("A quantidade de jogadores no time é inferior a 5!");
        }
    }

    private void validateThereGoalKeeper(Team team) {
        int qtde = 0;
        for (Player player : team.getPlayers()) {
            if (player.getClass().equals(Goalkeeper.class)) {
                qtde++;
            }
        }
        if (qtde > 1 || qtde < 1) {
            throw new RuntimeException("O time deve possuir pelo menos um goleiro! Existem : " + qtde);
        }
    }

    public void addGols(Player player) {
        Team team = player.getTeam();
        if (team.equals(home)) {
            gols[HOME] += 1;
        } else {
            gols[VISITOR] += 1;
        }
    }

    public void showGols() {
        System.out.println("Placar: Home " + gols[HOME] + "  -  " + gols[VISITOR] + " Visitor");
    }

    public void showChance() {
        int sum = visitor.getTotalSkill() + home.getTotalSkill();
        double cHome = (home.getTotalSkill() * 100 / sum) + 20;
        double cVisitor = (visitor.getTotalSkill() * 100 / sum) - 20;
        System.out.println("Home: " + cHome + "%\nVisitor: " + cVisitor + "%");
    }

    public void finshMatch() {
        if (gols[HOME] > gols[VISITOR]) {
            home.getResults().put(Result.VICTORY, home.getResults().get(Result.VICTORY) + 1);
            visitor.getResults().put(Result.LOSE, visitor.getResults().get(Result.LOSE) + 1);
        } else if ((gols[HOME] < gols[VISITOR])) {
            home.getResults().put(Result.LOSE, home.getResults().get(Result.LOSE) + 1);
            visitor.getResults().put(Result.VICTORY, visitor.getResults().get(Result.VICTORY) + 1);
        } else {
            home.getResults().put(Result.DRAW, home.getResults().get(Result.DRAW) + 1);
            visitor.getResults().put(Result.DRAW, visitor.getResults().get(Result.DRAW) + 1);
        }
    }
}
