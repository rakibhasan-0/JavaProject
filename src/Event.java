/**
 * The Event class is used to keep track of the information regarding an event. It contains the ID of
 * the event, the timestep when the event occurred and the node where the event occurred.
 */
public class Event {
    private final int ID;
    private final int time;
    private final Node node;

    /**
     * <p>
     *      Constructs an event object.
     * </p>
     *
     * @param ID   The ID of the event.
     * @param time The timestep when the event occured.
     * @param node The node where the event occurred.
     */
    public Event(int ID, int time, Node node){
        this.ID = ID;
        this.time = time;
        this.node = node;
    }

    /**
     * <p>
     *     Function to obtain the ID of an event.
     * </p>
     * @return the event ID.
     */
    public int getID(){
        return ID;
    }

    /**
     * <p>
     *     Function to obtain the time of occurrence of an event.
     * </p>
     * @return the time the event occurred.
     */
    public int getTime(){
        return time;
    }

    /**
     * <p>
     *     Function to obtain the node where the event occured..
     * </p>
     * @return the node where the event occurred.
     */
    public Node getNode() {
        return node;
    }

    @Override
    public String toString(){
        return("Event: [" + ID +", " + node + ", " + time + "]" );
    }
}
