import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class NodeTest {

    @Test
    void getPositionTest() {
        Position pos = new Position(1,0);
        Node node = new Node(pos);

        assertEquals(pos.getX(),node.getPosition().getX());
        assertEquals(pos.getY(),node.getPosition().getY());
    }

    @Test
    void isAvailableTest() {
        Position pos = new Position(1,0);
        Node node = new Node(pos);

        assertTrue(node.isAvailable());
    }

    @Test
    void getDistanceTest() {
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(10,0);
        Position pos3 = new Position(0,10);
        Position pos4 = new Position(10,10);

        Node node1 = new Node(pos1);
        Node node2 = new Node(pos2);
        Node node3 = new Node(pos3);
        Node node4 = new Node(pos4);

        assertEquals(node1.getDistance(node2), 10);
        assertEquals(node1.getDistance(node3), 10);
        assertEquals(node1.getDistance(node4), Math.sqrt(200));
    }

    @Test
    void neighbourTest() {
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(10,0);
        Position pos3 = new Position(0,10);
        Position pos4 = new Position(10,10);

        Node node1 = new Node(pos1);
        Node node2 = new Node(pos2);
        Node node3 = new Node(pos3);
        Node node4 = new Node(pos4);

        node1.addNeighbour(node2);
        node1.addNeighbour(node3);
        node1.addNeighbour(node4);

        assertEquals(node1.getNeighbours().size(), 3);
        assertEquals(node2.getNeighbours().size(), 0);


    }


    @Test
    void receiveTest() {
        Position pos1 = new Position(0,0);
        Node node1 = new Node(pos1);
        Object o = new Object();

        node1.receive(o);

        assertFalse(node1.isAvailable());

    }

    @Test
    void transmitTest() {
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(1,0);

        Node node1 = new Node(pos1);
        Node node2 = new Node(pos2);

        node1.addNeighbour(node2);
        node2.addNeighbour(node1);

        Event e = new Event(1,1, node1);
        Agent a = new Agent(node1, e, 10);

        node1.transmit();

        assertFalse(node1.isAvailable());
        assertFalse(node2.isAvailable());

    }

    @Test
    void ageAgentsAndQueriesTest() {
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(1,0);

        Node node1 = new Node(pos1);
        Node node2 = new Node(pos2);

        node1.addNeighbour(node2);
        node2.addNeighbour(node1);

        Event e = new Event(1,1, node1);
        Agent a = new Agent(node1, e, 5);

        node1.receive(a);



        for (int i = 0; i < 10; i++) {
            node1.ageAgentsAndQueries();
        }

        node1.transmit();

        assertTrue(node2.isAvailable());


    }

    @Test
    void eventInfoTest() {
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(1,0);

        Node node1 = new Node(pos1);
        Node node2 = new Node(pos2);

        node1.addNeighbour(node2);
        node2.addNeighbour(node1);

        Event e = new Event(1,1, node1);
        Agent a = new Agent(node1, e, 5);

        assertEquals(node2.getNodeMap().size(), 0);

        node1.receive(a);
        node1.transmit();

        assertEquals(node2.getNodeMap().size(),1);

    }



    @Test
    void getDistanceMapTest() {
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(1,0);
        Position pos3 = new Position(2,0);

        Node node1 = new Node(pos1);
        Node node2 = new Node(pos2);
        Node node3 = new Node(pos3);


        node1.addNeighbour(node2);
        node2.addNeighbour(node3);

        Event e = new Event(1,1, node1);
        Agent a = new Agent(node1, e, 5);

        node1.addEventInfoToNode(e.getID(), node1, 0, 1);

        node1.transmit();
        node2.transmit();

        assertEquals(node1.getDistanceMap().get(1), 0);
        assertEquals(node2.getDistanceMap().get(1),1);
        assertEquals(node3.getDistanceMap().get(1), 2);
    }

    @Test
    void getNodeMapTest() {
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(1,0);
        Position pos3 = new Position(2,0);

        Node node1 = new Node(pos1);
        Node node2 = new Node(pos2);
        Node node3 = new Node(pos3);

        node1.addNeighbour(node2);
        node2.addNeighbour(node3);

        Event e = new Event(1,1, node1);
        Agent a = new Agent(node1, e, 5);

        node1.addEventInfoToNode(e.getID(), node1, 0, 1);

        node1.transmit();
        node2.transmit();

        assertEquals(node1.getNodeMap().get(1), node1);
        assertEquals(node2.getNodeMap().get(1), node1);
        assertEquals(node3.getNodeMap().get(1), node2);
    }

    @Test
    void getTimeMapTest() {
        Position pos1 = new Position(0,0);

        Node node1 = new Node(pos1);


        node1.addEventInfoToNode(1, node1, 0, 1);

        assertEquals(1,node1.getTimeMap().get(1));

    }
}