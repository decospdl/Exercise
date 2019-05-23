package activityjunit;

import java.time.LocalDate;
import java.time.Month;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Administrator
 */
public class GoalkeeperTest {

    @Test
    public void testAge() {
        Goalkeeper gk = new Goalkeeper(LocalDate.of(1990, Month.MARCH, 12), 13, 1, 1);
        Assert.assertEquals(29, gk.getAge());
    }

    @Test
    public void testGetSkill() {
        Goalkeeper gk = new Goalkeeper(LocalDate.of(1990, Month.MARCH, 12), 13, 210, 1);
        Assert.assertEquals(406, gk.getSkill());
    }
}
