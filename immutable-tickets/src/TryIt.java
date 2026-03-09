import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Demonstrates immutability:
 * - Building a ticket
 * - "Updating" by creating a new instance (original unchanged)
 * - Tags list is not mutable from outside
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + t);

        // "Update" via service — returns a new ticket, original stays unchanged
        IncidentTicket assigned = service.assign(t, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);
        System.out.println("\nOriginal after updates: " + t);
        System.out.println("Assigned copy:         " + assigned);
        System.out.println("Escalated copy:        " + escalated);

        // Demonstrate that tags list is not mutable from outside
        List<String> tags = t.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
            System.out.println("\nERROR: tags should be immutable but were modified!");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nExternal tag mutation blocked (UnsupportedOperationException) — immutability works!");
        }
    }
}
