import java.io.IOException;
import java.util.*;

/**
 * The Network class is used to construct a network of sensor-nodes as well as update the network.
 */
public class Network {

    private final ArrayList<Node> nodeList;
    private final ArrayList<Node> queryNodes;

    private final Environment environment;

    private final int lifeTimeForAgent;
    private final int lifeTimeForQuery;
    private final int QUERY_TIME;

    private final double maxDistance;
    private final double agentProbability;
    private HashMap<Query,Integer> queryList;

    private int timeCounter;

    /**
     * <p>
     *     Constructs a Network and fills it with nodes according to the input Scanner parameter
     *     from the .txt file containing the nodes.
     * </p>
     *
     * @throws IOException If there is something wrong with the input file.
     * @param sc Scanner object describing the node positions in the network.
     * @param probAgent probablility that an agent will be created at an event.
     * @param probEvent probablility that an event will occur in a node at a time-step.
     * @param maxDistance maximum distance to neighbour node.
     * @param queryTime Timesteps between the creation of new queries.
     * @param lifeTimeForAgent timesteps an agent will live before it dies.
     * @param lifeTimeForQuery timesteps a query will live before it dies if it can't find a path to its event.
     */
    public Network(Scanner sc, double probAgent, double probEvent, double maxDistance, int queryTime, int lifeTimeForAgent, int lifeTimeForQuery) throws IOException {

        // Set some constants
        agentProbability = probAgent;
        this.lifeTimeForAgent = lifeTimeForAgent;
        this.lifeTimeForQuery = lifeTimeForQuery;
        this.maxDistance = maxDistance;

        timeCounter = 0;
        QUERY_TIME = queryTime;

        // Initialize the environment
        environment = new Environment(probEvent);

        // Initialize some Lists
        nodeList = new ArrayList<>();
        queryNodes = new ArrayList<>();
        queryList = new HashMap<>();



        // Read the file containing the nodes
        int nrOfNodes = sc.nextInt();

        sc.nextLine();

        int counter = 0;
        // Scan the file containing the network.
        while(sc.hasNextLine() && counter <= nrOfNodes) {
            String line =  sc.nextLine();
            String[] arrOfLine = line.split(",");
            int x = Integer.parseInt(arrOfLine[0]);
            int y = Integer.parseInt(arrOfLine[1]);

            nodeList.add(new Node(new Position(x,y)));

            counter++;
        }

        if (nrOfNodes != counter){
            throw new IOException("Expected Number of nodes: "+nrOfNodes+". Actual number of nodes: "+counter+".");
        }

        // Calculate all node-neighbour connections.
        calculateNeighbours();

        // Add nodes as query-nodes.
        setQueryNodes();
    }



    /**
     * <p>
     *     Function to calculate all neighbours of the nodes in the network.
     * </p>
     *
     */
    public void calculateNeighbours(){
        for (Node node : nodeList){
            for (Node neigh : nodeList) {
                if (node.getDistance(neigh) <= maxDistance && node!=neigh) {
                    node.addNeighbour(neigh);
                }
            }
        }

    }


    /**
     * <p>
     *     Creates an agent at a node in the network.
     * </p>
     * @param node the node where the agent is to be placed.
     * @param event that created the Agent.
     */
    public void createAgent(Node node, Event event){
        new Agent(node, event, lifeTimeForAgent);
    }

    /**
     * <p>
     *     Creates a query at a node in the network.
     * </p>
     * @param node the node where the query is to be placed.
     */
    public void createQuery(Node node){
        Event e = environment.getRandomEvent();
        if(e != null) {
            Query q = new Query(node, e.getID(), lifeTimeForQuery);
            queryList.put(q,e.getID());
        }
    }

    /**
     * <p>
     *     Updates the network by creating new events, creating new queries,
     *     creating new agents and moving existing agents and queries around in the network.
     * </p>
     */
    public void updateNetwork(){

        // Increase the time-counter
        timeCounter++;

        // Update each node in the network before transmission stage.
        for (Node node : nodeList){
            node.available = true;
            node.ageAgentsAndQueries();

            // Create new event and new agents.
            Event e = environment.eventOccured(node,timeCounter);
            if(e != null){
                node.addEventInfoToNode(e.getID(), node, 0, e.getTime());
                double randAgent = Math.random();
                if (randAgent <= agentProbability){
                    createAgent(node,e);
                }
            }
        }


        // Create Queries at fixed time intervals.
        if(timeCounter % QUERY_TIME == 0){
            for(Node node : queryNodes) {
                createQuery(node);
            }
        }

        // Transmit Agents and Queries between nodes.
        for (Node node : nodeList){
            if(node.isAvailable()) {
                node.transmit();
            }
        }
    }

    /**
     * Function túsed to set the nodes where queries are created.
     * @throws IOException something wrong with network
     */
    private void setQueryNodes() throws IOException {
        if(nodeList.size()>=4) {
            Collections.shuffle(nodeList);
            queryNodes.add(nodeList.get(0));
            queryNodes.add(nodeList.get(1));
            queryNodes.add(nodeList.get(2));
            queryNodes.add(nodeList.get(3));
        } else {
            throw new IOException("Not enough nodes. Add at least four nodes.");
        }
    }

    /**
     * for the test purpose. It is not part of the API.
     */
    public HashMap<Query,Integer>getQueryNode(){
        return queryList;
    }

    /**
     * for the test purpose. It is not part of the API.
     */
    public void removeElementfromTheQuery(){
        queryList.clear();
    }
}
