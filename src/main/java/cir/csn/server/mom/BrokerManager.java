package cir.csn.server.mom;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

public class BrokerManager {
    private BrokerService service;

    public static void main(String[] args) {
    	BrokerManager example = new BrokerManager();
        System.out.println("Starting the Embedded Broker example now...");
        
        try {
            example.service = BrokerFactory.createBroker("xbean:activemq.xml");
            example.service.start();
            
            System.out.println("Press any key to stop broker...");
            System.in.read();
        } catch (Exception e) {
            System.out.println("Caught an exception during the example: " + e.getMessage());
        }
        System.out.println("Finished running the Embedded Broker example.");
        System.out.print("\n\n\n");
    }
}
