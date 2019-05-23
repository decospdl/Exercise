package activityjunit;

import java.time.LocalDate;

/**
 *
 * @author Administrator
 */
public class Goalkeeper extends Player {

    private int high;
    private int reflex;

    public Goalkeeper(LocalDate birthDay, int number, int high, int reflex) {
        super(birthDay, number);
        this.high = high;
        this.reflex = Player.validateSkills(reflex);
    }

    private int calculatHighSkill() {   
        return (high >= 210) ? 100 : high * 100 / 210;
    }
   
    @Override
    public int getSkill() {
        return calculatHighSkill() * 4 + reflex * 6;
    }

}
