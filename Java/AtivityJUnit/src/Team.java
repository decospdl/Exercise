package activityjunit;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class Team {

    private static final int MAX_PLAYERS = 5;

    private final ArrayList<Player> players;
    private String name;
    private HashMap<Result, Integer> results;
    private int totalSkill;

    public Team(String name) {
        this.name = name;
        players = new ArrayList<>();
        initResults();
    }

    private void initResults() {
        results = new HashMap<>();
        results.put(Result.LOSE, 0);
        results.put(Result.DRAW, 0);
        results.put(Result.VICTORY, 0);
    }

    public boolean isTeamFull() {
        if (players.size() == MAX_PLAYERS) {
            return true;
        }
        return false;
    }

    public void addPlayer(Player player) {
        if (!isTeamFull()) {
            player.setTeam(this);
            players.add(player);
        }
        calculateTotalSkill();
    }

    public void removePlayer(Player player) {
        players.remove(player);
        calculateTotalSkill();
    }

    public int getTotalSkill() {
        return totalSkill;
    }

    private void calculateTotalSkill() {
        int sum = 0;
        for (Player player : players) {
            sum += player.getSkill();
        }
        totalSkill = sum;
    }

    public HashMap<Result, Integer> getResults() {
        return results;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public void showResults(){
        int victory = results.get(Result.VICTORY);
        int loses = results.get(Result.LOSE);
        int draws = results.get(Result.DRAW);
        System.out.println("Vitórias: "+ victory);
        System.out.println("Derrotas: "+ loses);
        System.out.println("Empates: "+ draws);
        System.out.println("Pontos: "+ (victory * 3 + draws));
    }
}
