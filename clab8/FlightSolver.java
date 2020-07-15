import java.util.*;

public class FlightSolver {

    private int maxNum;

    public FlightSolver(ArrayList<Flight> flights) {
        maxNum = 0;

        PriorityQueue<Flight> startMinPQ = new PriorityQueue<>(new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                return o1.startTime() - o2.startTime();
            }
        });

        PriorityQueue<Flight> endMinPQ = new PriorityQueue<>(new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                return o1.endTime() - o2.endTime();
            }
        });

        startMinPQ.addAll(flights);
        endMinPQ.addAll(flights);

        int tally = 0;
        while (startMinPQ.size() != 0 && endMinPQ.size() != 0) {
            if (startMinPQ.peek().startTime() <= endMinPQ.peek().endTime()) {
                tally += startMinPQ.poll().passengers();
                maxNum = tally > maxNum ? tally : maxNum;
            }
            else {
                tally -= endMinPQ.poll().passengers();
            }
        }
    }

    public int solve() {
        return maxNum;
    }
}
//below is my original solution. The runtime is quadratic.

/*
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */

/*
public class FlightSolver {

    private class flightList {
        private ArrayList<Flight> al;
        private int numPassenger;
        private int startTime;
        private int endTime;

        public flightList(Flight flight) {
            al = new ArrayList<>();
            al.add(flight);
            numPassenger = flight.passengers();
            startTime = flight.startTime();
            endTime = flight.endTime();
        }

        public flightList(flightList fl) {
            al = new ArrayList<>(fl.al);
            numPassenger = fl.numPassenger;
            startTime = fl.startTime;
            endTime = fl.endTime;
        }

        public int numPassenger() {
            return numPassenger;
        }

        public void add(Flight flight) {
            al.add(flight);
            numPassenger += flight.passengers();
            startTime = Math.max(startTime, flight.startTime());
            endTime = Math.min(endTime, flight.endTime());
        }

        private boolean shouldHave(Flight flight) {
            if (flight.startTime() > endTime || flight.endTime < startTime) {
                return false;
            }
            return true;
        }
    }

    private PriorityQueue<flightList> pq;
    private ArrayList<flightList> alfl;

    public FlightSolver(ArrayList<Flight> flights) {
        Comparator<flightList> numPassengerComparator = (fl1, fl2) -> fl2.numPassenger() - fl1.numPassenger();
        pq = new PriorityQueue<>(11, numPassengerComparator);
        alfl = new ArrayList<>();
        for (Flight f: flights) {
            for (flightList fl: new ArrayList<flightList>(alfl)) {
                if (fl.shouldHave(f)) {
                    flightList temp = new flightList(fl);
                    temp.add(f);
                    alfl.add(temp);
                }
            }
            alfl.add(new flightList(f));
        }
    }

    public int solve() {
        for (flightList fl: alfl) {
            pq.add(fl);
        }
        return pq.peek().numPassenger();
    }
} */
