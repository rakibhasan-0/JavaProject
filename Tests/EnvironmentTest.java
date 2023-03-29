import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {

    @Test
    void eventOccurredTest() {

        Environment env = new Environment(1);
        Node node = new Node(new Position(1, 1));

        assertNull(env.getRandomEvent());

        env.eventOccured(node, 1);

        assertNotNull(env.getRandomEvent());

    }


}