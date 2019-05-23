package activityjunit;

import java.time.LocalDate;
import java.time.Month;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class AttackerTest {
    
    @Test
    public void testGetSkill() {
        Attacker at = new Attacker(LocalDate.of(1980, Month.MARCH, 11), 13, 1, 1);
        assertEquals(10, at.getSkill());
    }
    
}
