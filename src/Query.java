import java.util.*;

/**
 * The class Query is a long-lived message in a network of nodes which searches for a certain event.
 * It will move randomly in the network until it finds a path to the event. Once the path is found it
 * will follow the path until it reaches the event and will then go back along its path back to its
 * start position where it will die.
 */
public class Query {
    // counter used to count number of successful queries.
    public static int queryCounter = 0;
    public static int successCounter = 0;

    private Node currentNode;
    private Node previousNode;

    private final Stack<Node> path;

    private int ageCounter;
    private final int lifeTime;

    private boolean eventFound;
    private boolean pathFound;

    // Information to be filled once event is found.
    private final int targetID;
    private Node targetNode;
    private int targetTime;


    /**
     * <p>
     *     Constructs a Query in the network. A query will move randomly in the network for a number of timesteps
     *     before it dies. It will stop "aging" once a path is found.
     * </p>
     *
     * @param node node where the query is created.
     * @param ID ID of the event the query is looking for
     * @param lifeTime the number of timesteps the query will live before it dies.
     */
    public Query(Node node, int ID, int lifeTime){
        queryCounter++;

        currentNode = node;
        previousNode = node;

        targetID = ID;

        ageCounter = 0;
        this.lifeTime = lifeTime;

        path = new Stack<>();
        path.push(node);

        currentNode.receive(this);
    }

    /**
     * <p>
     *     Function used to retrieve what node the Query would like to move to.
     * </p>
     *
     * @return a list of nodes that the Agent would like to move to.
     */
    public ArrayList<Node> preferredMove(){
        readNode();

        // If it has already found the event it wants to move back along the path
        if (eventFound) {
            return backTrackMove();
        }

        // If it has found a path it wants to follow it
        if (pathFound){
            return followMove();
        }

        // By default, the query wants to do a random move.
        return randomMove();

    }

    /**
     * Adds one timestep to the "age" of the query.
     * NOTE: Only adds age to a "lost" query.
     */
    private void addAge(){
        if(!eventFound) {
            ageCounter++;
        }
    }

    /**
     * <p>
     *     Age the query and returns if it has lived longer than its set lifetime.
     * </p>
     *
     * @return true if the query has lived longer than its set lifetime
     */
    public boolean wantsToDie(){
        addAge();
        return ageCounter > lifeTime;
    }

    /**
     * Shuffles a list of available nodes and puts the previous position last.
     */
    private ArrayList<Node> randomMove(){
        ArrayList<Node> nodes = currentNode.getNeighbours();

        Collections.shuffle(nodes);

        // Puts the previousNode last.
        nodes.remove(previousNode);
        nodes.add(nodes.size(),previousNode);

        return nodes;
    }

    /**
     * Moves the query along a path to an event.
     */
    private ArrayList<Node> followMove(){

        ArrayList<Node> n = new ArrayList<>();

        Node node = currentNode.getNodeMap().get(targetID);
        n.add(node);

        return n;

    }

    /**
     * Moves the query back along its path it took to reach the event.
     */
    private ArrayList<Node> backTrackMove(){
        path.pop();
        if ( path.isEmpty() ) {
            // Framme :)
            System.out.println("Found Event: " + targetID + " at " + targetNode + " it occured at timestep " + targetTime + "." );
            successCounter++;
            return null;

        }

        ArrayList<Node> nodeList = new ArrayList<>();
        nodeList.add(path.peek());

        return nodeList;

    }

    /**
     * <p>
     *     Set the current node that the agent resides at. Also updates the previous node.
     * </p>
     *
     * @param node to put the query at
     */
    public void move(Node node){
        previousNode = currentNode;
        currentNode = node;

        if (!eventFound) {
            path.push(node);
        }


    }

    /**
     * Inspects a node and checks if it contains information about the event of interest.
     */
    private void readNode(){

        if(currentNode.getNodeMap() != null) {
            for (Map.Entry<Integer, Node> info : currentNode.getNodeMap().entrySet()) {
                Integer ID = info.getKey();
                Node n = info.getValue();

                if (ID.equals(targetID)) {
                    pathFound = true;

                    if(n.equals(currentNode)){
                        eventFound = true;
                        targetNode = currentNode;
                        targetTime = currentNode.getTimeMap().get(ID);
                    }

                }

            }
        }

    }


    @Override
    public String toString(){
        return("Query");
    }
}
