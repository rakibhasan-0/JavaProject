import java.util.*;

/**
 * The class Agent is a long-lived message in a network of nodes that can be used to spread information between
 * nodes in the network.
 */
public class Agent {
    private Node currentNode;
    private Node previousNode;
    private final LinkedHashMap<Integer, Integer> eventList;
    private int ageCounter;
    private final int lifeTime;


    /**
     * <p>
     *     Constructs a new Agent object at a node in a network. It also collects any information that is in
     *     the node it is created at.
     * </p>
     *
     * @param node the node where the agent is put.
     * @param event the event that created the agent.
     * @param lifeTime the number of timesteps before the agent dies.
     */
    public Agent(Node node, Event event, int lifeTime){
        currentNode = node;
        previousNode = node;
        ageCounter = 0;
        this.lifeTime = lifeTime;

        eventList = new LinkedHashMap<>(1000);
        eventList.put(event.getID(), 0);
        node.receive(this);
        readNode();
    }


    /**
     * <p>
     *     Function used to retrieve what node the Agent would like to move to.
     * </p>
     *
     * @return a list of nodes that the Agent would like to move to.
     */
    public ArrayList<Node> preferredMove(){
        // Obtains the current node neighbours
        ArrayList<Node> nodes = currentNode.getNeighbours();

        // Shuffle list of neighbours
        Collections.shuffle(nodes);

        // Puts the previousNode last.
        nodes.remove(previousNode);
        nodes.add(nodes.size(),previousNode);

        return nodes;
    }


    /**
     * <p>
     *     Increments the distance value for all events the agent knows about.
     * </p>
     */
    private void incrementEventList(){
        if(!eventList.isEmpty()) {
            for (Map.Entry<Integer, Integer> info : eventList.entrySet()) {
                Integer ID = info.getKey();
                Integer distance = info.getValue();

                eventList.replace(ID, distance+1);

            }
        }
    }


    /**
     * <p>
     *     Moves the Agent to a new node and syncs the information contained
     *     in the agent with the information in the node.
     * </p>
     *
     * @param node the Node the agent is moved to.
     */
    public void moveAndShare(Node node){
        previousNode = currentNode;
        currentNode = node;

        incrementEventList();
        readNode();
        spreadRumors();
    }

    /**
     * <p>
     *     Syncronizes the information in the Agent with the current Node.
     * </p>
     *
     */
    private void spreadRumors(){
        if(!eventList.isEmpty()) {
            for (Map.Entry<Integer, Integer> info : eventList.entrySet()) {
                Integer ID = info.getKey();
                Integer distance = info.getValue();

                currentNode.updateEventInfo(ID, previousNode, distance);

            }
        }
    }

    /**
     * Reads the information in a node and updates the Agent about events.
     */
    private void readNode(){
        if (currentNode.getDistanceMap()!=null) {
            for (Map.Entry<Integer, Integer> entry : currentNode.getDistanceMap().entrySet()) {
                Integer ID = entry.getKey();
                Integer distance = entry.getValue();

                if (!eventList.containsKey(ID)) {
                    eventList.put(ID, distance);
                } else if (eventList.get(ID) > distance){
                    eventList.replace(ID, distance);
                }
            }
        }
    }

    /**
     * Adds one timestep to the "age" of the agent.
     */
    private void addAge(){
        ageCounter++;
    }

    /**
     * <p>
     *     Function used to age the agent and check if the agent age has exceeded its lifetime.
     * </p>
     *
     * @return true if the Agent age has exceeded its lifetime.
     */
    public boolean wantsToDie(){
        addAge();
        return ageCounter > lifeTime;
    }

    /**
     * <p>
     *     Function used to get current nude of an agent. Used mainly for testing
     * </p>
     *
     * @return Returns the current node of an agent.
     */
    public Node getCurrentNode() {
        return currentNode;
    }

    @Override
    public String toString(){
        return( "AGENT " + ageCounter );
    }



}
