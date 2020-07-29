package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private LinkedList<Vertex> solution;
    private double timeSpent;
    private int numStates;

    private ArrayHeapMinPQ<Vertex> pq;
    private Map<Vertex, Double> distTo;
    private Map<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        pq = new ArrayHeapMinPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        pq.add(start, 0);
        distTo.put(start, 0.0);
        edgeTo.put(start, null);
        numStates = 0;
        while (pq.size() != 0) {
            if (pq.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                solutionWeight = distTo.get(pq.getSmallest());
                solution = new LinkedList<Vertex>();
                Vertex v = pq.getSmallest();
                solution.addFirst(v);
                while (!v.equals(start)) {
                    solution.addFirst(edgeTo.get(v));
                    v = edgeTo.get(v);
                }
                timeSpent = sw.elapsedTime();
                return;
            }
            timeSpent = sw.elapsedTime();
            if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solutionWeight = 0;
                solution = new LinkedList<Vertex>();
                return;
            }
            Vertex p = pq.removeSmallest();
            numStates += 1;
            for (WeightedEdge<Vertex> e : input.neighbors(p)) {
                if (!distTo.containsKey(e.to())) {
                    distTo.put(e.to(), Double.POSITIVE_INFINITY);
                }
                relax(input, end, edgeTo, e);
            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
        solutionWeight = 0;
        solution = new LinkedList<Vertex>();
        timeSpent = sw.elapsedTime();
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStates;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }

    private void relax(AStarGraph<Vertex> input,Vertex end, Map<Vertex, Vertex> edgeTo, WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();

        if (distTo.get(p) + w < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(e.to(), p);
            if (pq.contains(q)) {
                pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            } else {
                pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            }
        }
    }
}
