package creatures;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the plip class
 *  @authr Joshua
 */

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
        Plip p = new Plip(2);
        c.attack(p);
        assertEquals(3.92, c.energy(), 0.01);
        Clorus c2 = new Clorus(20);
        c.attack(c2);
        assertEquals(23.92, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(1);
        Clorus offspring = c.replicate();
        assertNotEquals(c, offspring);
        assertEquals(0.5, c.energy(), 0.01);
        assertEquals(0.5, offspring.energy(), 0.01);
        Clorus offspring2 = offspring.replicate();
        assertEquals(0.25, offspring.energy(), 0.01);
        assertEquals(0.25, offspring2.energy(), 0.01);
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(10);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        // Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
        c = new Clorus(10.0);
        Plip p = new Plip(1.0);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, p);
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        Action actual2 = c.chooseAction(topEmpty);
        Action expected2 = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        assertEquals(expected2, actual2);


        // Otherwise, if the Clorus has energy >= 1, it will REPLICATE to a random empty square.
        c = new Clorus(10);
        HashMap<Direction, Occupant> topEmpty2 = new HashMap<Direction, Occupant>();
        topEmpty2.put(Direction.TOP, new Empty());
        topEmpty2.put(Direction.BOTTOM, new Impassible());
        topEmpty2.put(Direction.LEFT, new Impassible());
        topEmpty2.put(Direction.RIGHT, new Impassible());

        Action actual3 = c.chooseAction(topEmpty2);
        Action expected3 = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected3, actual3);

        //Otherwise, the Clorus will MOVE to a random empty square.
        Clorus c2 = new Clorus(0.2);
        Action actual4 = c2.chooseAction(topEmpty2);
        Action expected4 = new Action(Action.ActionType.MOVE, Direction.TOP);

        assertEquals(expected4, actual4);
    }
}
