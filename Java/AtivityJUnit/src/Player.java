package activityjunit;

import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author
 */
public abstract class Player {

    private int gols;
    private final LocalDate birthDay;
    private final int number;
    private Team team;

    public Player(LocalDate birthDay, int number) {
        this.gols = 0;
        this.birthDay = birthDay;
        this.number = number;
        this.team = null;
    }

    public int getGols() {
        return gols;
    }

    public void addGols(int gols) {
        this.gols += gols;
    }

    public int getAge() {
        return Period.between(birthDay, LocalDate.now()).getYears();
    }

    public int getNumber() {
        return number;
    }
    
    public void setTeam(Team team){
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
    
    

    public abstract int getSkill();

    public static int validateSkills(int skill) {
        if (skill < 0) {
            return 0;
        }
        else if(skill > 100)
        {
            return 100;
        }
        return skill;
    }
}
