package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private List<Node> nodes;
    private Map<String, List<Node>> nameNodeMap;
    private MyTrieSet trieSet;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        nodes = this.getNodes();
        nameNodeMap = new HashMap<>();
        trieSet = new MyTrieSet();
        for (Node n : nodes) {
            if (n.name() != null) {
                String nameOfN = cleanString(n.name());
                if (nameNodeMap.containsKey(nameOfN)) {
                    nameNodeMap.get(nameOfN).add(n);
                } else {
                    List<Node> listOfNodes = new ArrayList<>();
                    listOfNodes.add(n);
                    nameNodeMap.put(nameOfN, listOfNodes);
                    trieSet.add(nameOfN);
                }
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        List<Point> points = new ArrayList<>();
        Map<Point, Node> pointNodeMap = new HashMap<>();

        for (Node n : nodes) {
            if (!this.neighbors(n.id()).isEmpty()) {
                Point p = new Point(n.lon(), n.lat());
                points.add(p);
                pointNodeMap.put(p, n);
            }
        }

        KDTree kdTree = new KDTree(points);
        Point nearest = kdTree.nearest(lon, lat);
        long id = pointNodeMap.get(nearest).id();
        return id;
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String pref = cleanString(prefix);
        List<String> cleanNameList = trieSet.keysWithPrefix(pref);
        List<String> results = new LinkedList<>();
        for (String s : cleanNameList) {
            List<Node> nodeList = nameNodeMap.get(s);
            for (Node n : nodeList) {
                results.add(n.name());
            }
        }
        return results;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanedName = cleanString(locationName);
        List<Node> nodeList = nameNodeMap.get(cleanedName);
        List<Map<String, Object>> results = new ArrayList<>();
        for (Node n : nodeList) {
            Map<String, Object> map = new HashMap<>();
            map.put("lat", n.lat());
            map.put("lon", n.lon());
            map.put("name", n.name());
            map.put("id", n.id());
            results.add(map);
        }
        return results;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
