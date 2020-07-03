package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;
/**
 * An implementation of a fierce blue-colored pacifist predator.
 *
 * @author Joshua Su
 */

public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        energy *= 0.5;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();

        for (Direction dir : neighbors.keySet()) {
            if (neighbors.get(dir).name().equals("empty")) {
                emptyNeighbors.add(dir);
            }
        }

        for (Direction dir : neighbors.keySet()) {
            if (neighbors.get(dir).name().equals("plip")) {
                plipNeighbors.add(dir);
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plipNeighbors.size() > 0) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
        } else if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }

    @Override
    public Color color() {
        return color(r, g, b);
    }
}
