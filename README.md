Overview

This project simulates a network of wireless sensor nodes with short radio transmission range. Nodes detect unique events. Using the rumor-routing algorithm, a node in the network can query about an event, and the query will be routed to the node that detected that event.
Features

    Nodes: Each node maintains:
        A list of its neighbors.
        An event table with directions for routing a query.
        An estimate of the number of steps to the event (routing table).

    Event Detection:
        On detection, a node updates its table, marking the event distance as 0.
        Events are recorded with a unique ID, timestamp, and location.
        Detection probability is 0.01% per timestep per node.

    Agent Messages:
        Generated with a 50% probability upon event detection.
        Contains an event table and helps sync information across the network.
        An agent can hop a constant number of times in the network.

    Queries:
        Can be initiated from any node.
        Routed towards the node that detected the queried event.
        Maximum hops for a query is 45 timesteps.

    Communication Constraints:
        Nodes can either send or receive a message in a timestep, not both.
        Pending messages are queued for the next timestep.
