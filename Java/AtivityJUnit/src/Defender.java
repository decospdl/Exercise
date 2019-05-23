package activityjunit;

import java.time.LocalDate;

/**
 *
 * @author Administrator
 */
public class Defender extends Player {

    private final int coberture;
    private final int disarm;

    public Defender(LocalDate birthDay, int number, int coberture, int disarm) {
        super(birthDay, number);
        this.coberture = Player.validateSkills(coberture);
        this.disarm = Player.validateSkills(disarm);
    }

    @Override
    public int getSkill() {
        return coberture * 6 + disarm * 4;
    }
}
