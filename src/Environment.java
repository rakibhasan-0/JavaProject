import java.util.ArrayList;
import java.util.Collections;


/**
 * <p>
 *     The Environment class is used to keep track of events and create new events.
 * </p>
 */
public class Environment {

    private final ArrayList<Event> eventList;
    private final double eventProbability;


    /**
     * <p>
     *     Creates a new Environment with a certain probability to create new events.
     * </p>
     *
     * @param eventProbability the probability that a new event will occur.
     */
    public Environment(double eventProbability){
        eventList = new ArrayList<>();
        this.eventProbability = eventProbability;
    }


    /**
     * <p>
     *     Allows for an event to occur at a specific node. Sets the event ID to equal the number of
     *     events that has occured so far in the environment.
     * </p>
     *
     * @param node node that the event occurs at.
     * @param time timeStep that the event occurred at.
     * @return the created event or null.
     */
    public Event eventOccured(Node node, int time){

        double randEvent = Math.random();
        if (randEvent <= eventProbability){
            Event e = new Event(eventList.size(), time, node);
            eventList.add(e);
            return e;
        }
    return null;
    }

    /**
     * <p>
     *     Function used to obtain a random event in the eventList.
     * </p>
     *
     * @return a random event that has occurred in the environment or null if no event has occurred.
     */
    public Event getRandomEvent(){
        if(!eventList.isEmpty()) {
            Collections.shuffle(eventList);
            return eventList.get(0);
        }
        return null;
    }


}
