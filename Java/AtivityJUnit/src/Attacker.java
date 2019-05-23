package activityjunit;

import java.time.LocalDate;

/**
 *
 * @author Administrator
 */
public class Attacker extends Player {

    private final int velocity;
    private final int tecnic;

    public Attacker(LocalDate birthDay, int number, int velocity, int tecnic) {
        super(birthDay, number);
        this.velocity = Player.validateSkills(velocity);
        this.tecnic = Player.validateSkills(tecnic);
    }

    @Override
    public int getSkill() {
        return velocity * 4 + tecnic * 6;
    }

}
