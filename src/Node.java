import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * The Node class is used to create node objects that contain information about events and transmit
 * Agents and Queries in a network of nodes.
 */

public class Node {

    private final Position position;

    private final LinkedHashMap<Integer, Node> IDToNodeMap;  // hashmap: EventID -> Node
    private final LinkedHashMap<Integer, Integer> IDToDistanceMap; // hashmap: EventID -> distance
    private final LinkedHashMap<Integer, Integer> IDToTimeMap; // Hashmap: EventID -> Event time

    private final ArrayList<Node> neighbours;
    private final ArrayList<Object> outGoingList;

    public boolean available;

    /**
     * Initializes a node at a given position.
     *
     * @param pos the position where the node is created.
     */
    public Node(Position pos){
        position = pos;
        neighbours = new ArrayList<>();
        outGoingList = new ArrayList<>();

        IDToNodeMap = new LinkedHashMap<>(1000);
        IDToDistanceMap = new LinkedHashMap<>(1000);
        IDToTimeMap = new LinkedHashMap<>(1000);

        available = true;

    }

    /**
     * <p>
     *     Function used to get the position of a node.
     * </p>
     *
     * @return a position with x and y coordinates.
     */
    public Position getPosition() {
        return new Position(position.getX(), position.getY());
    }

    /**
     * <p>
     *     Indicates if a node is available to send or receive a message.
     * </p>
     *
     * @return true if the node is available.
     */
    public boolean isAvailable(){
        return available;
    }

    /**
     * <p>
     *     Function used to calculate the distance between two nodes. Uses Pythagoras theorem.
     * </p>
     *
     * <p>
     *     https://en.wikipedia.org/wiki/Pythagorean_theorem
     * </p>
     *
     * @param node the node to calculate distance to.
     * @return double value of the distance between the nodes.
     */
    public double getDistance(Node node) {
        int x1 = getPosition().getX();
        int y1 = getPosition().getY();

        int x2 = node.getPosition().getX();
        int y2 = node.getPosition().getY();

        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }

    /**
     * <p>
     *     Function used to obtain the neighbours of the node.
     * </p>
     *
     * @return an ArrayList of nodes.
     */
    public ArrayList<Node> getNeighbours(){
        return neighbours;
    }

    /**
     *
     * <p>
     *     Adds a node to the neighbour list.
     * </p>
     *
     * @param node that is to be added.
     */
    public void addNeighbour(Node node) {
        neighbours.add(node);
    }

    @Override
    public String toString(){
        return "(" + position.getX() + ", " + position.getY()+ ")";
    }

    /**
     * <p>
     *     Function used in communication between nodes on the receiving side. Adds an Agent or Query
     *     to the node and sets the node to unavailable.
     * </p>
     * @param o the object that is sent.
     */
    public void receive(Object o){
        outGoingList.add(o);
        available = false;
    }

    /**
     * <p>
     *     Sends the first object in the outgoing list to a neighbouring node. After the
     *     object is sent, it is removed from the node.
     * </p>

     */
    public void transmit(){

        if (!outGoingList.isEmpty()) {
            if (outGoingList.get(0) instanceof Agent) {

                // Send an agent
                Agent o = (Agent) outGoingList.get(0);
                ArrayList<Node> nodes = o.preferredMove();

                for (Node node : nodes) {
                    if (node.isAvailable()) {
                        node.receive(o);
                        outGoingList.remove(o);
                        o.moveAndShare(node);
                        break;
                    }
                }

            } else {

                // Send a Query
                Query o = (Query) outGoingList.get(0);
                ArrayList<Node> nodes = o.preferredMove();

                if(nodes == null) {
                    outGoingList.remove(o);
                } else {
                    for (Node node : nodes) {

                        if (node.isAvailable()) {
                            node.receive(o);
                            outGoingList.remove(o);
                            o.move(node);
                            break;
                        }
                    }
                }
            }

            available = false;

        }
    }

    /**
     * <p>
     *     Ages and removes too old agents/queries.
     * </p>
     *
     */
    public void ageAgentsAndQueries(){
        ArrayList<Agent> killAgents = new ArrayList<>();
        ArrayList<Query> killQuerys = new ArrayList<>();

        for(Object o : outGoingList){
            if (o instanceof Agent && ((Agent) o).wantsToDie()) {
                killAgents.add((Agent) o);
            }
            if (o instanceof Query && ((Query) o).wantsToDie()) {
                killQuerys.add((Query) o);
            }
        }

        for(Agent a : killAgents){
            outGoingList.remove(a);
        }
        for(Query q : killQuerys){
            outGoingList.remove(q);
        }

    }

    /**
     * <p>
     *     Adds new event info to the node.
     * </p>
     *
     * @param ID ID of the event
     * @param node node of the event
     * @param distance distance to the event
     */
    public void addEventInfoToNode(int ID, Node node, int distance){
        IDToDistanceMap.put(ID, distance);
        IDToNodeMap.put(ID,node);
    }

    /**
     * <p>
     *     Adds new event info to the node.
     * </p>
     *
     *
     * @param ID ID of the event
     * @param node node of the event
     * @param distance distance to the event
     * @param time the time the event occurred
     */
    public void addEventInfoToNode(int ID, Node node, int distance, int time){
        IDToDistanceMap.put(ID, distance);
        IDToNodeMap.put(ID,node);
        IDToTimeMap.put(ID, time);
    }

    /**
     * <p>
     *     Updates the current Event-info in the node. If the new info contains a path to an event which
     *     is shorter than the current path, it is updated.
     * </p>
     *
     * @param ID ID of the event
     * @param node node of the event
     * @param distance distance to the event
     */
    public void updateEventInfo(int ID, Node node, int distance){
        if (IDToDistanceMap.containsKey(ID)) {
            if(IDToDistanceMap.get(ID) > distance){
                IDToDistanceMap.replace(ID, distance);
                IDToNodeMap.replace(ID, node);
            }

        } else {
            addEventInfoToNode(ID, node, distance);
        }

    }

    /**
     * <p>
     *     Function used to get the information regarding distances.
     * </p>
     * @return Map of ID and distances
     */
    public HashMap<Integer,Integer> getDistanceMap(){
        return IDToDistanceMap;
    }

    /**
     * <p>
     *      Function used to get the information regarding nodes.
     * </p>
     * @return Map of ID and nodes
     */
    public HashMap<Integer,Node> getNodeMap() {
        return IDToNodeMap;
    }

    /**
     * <p>
     *      Function used to get the information regarding time.
     * </p>
     * @return Map of ID and times
     */
    public HashMap<Integer,Integer> getTimeMap() {
        return IDToTimeMap;
    }


}


