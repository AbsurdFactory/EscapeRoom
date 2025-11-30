package ticket.service;

import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import org.junit.jupiter.api.Test;
import ticket.dao.TicketDaoImpl;
import ticket.model.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketServiceTest {
    LocalDateTime localDateTime = LocalDateTime.now();
    TicketDaoImpl ticketDaoImp = new TicketDaoImpl();
    TicketService ticketService = new TicketService(ticketDaoImp);

    @Test
    void createTicket() {
        Ticket ticket1 = new Ticket(new Name("john"), new Name("room1"), localDateTime, new BigDecimal(100));
        ticketService.createTicket(ticket1);

        assertEquals(new Name("john"), ticket1.getPlayerName());
    }

    @Test
    void getTicketById() {
        Ticket ticket2 = ticketService.getTicketById(new Id<>(5));
        assertTrue(ticket2.getId().getValue() > 0);
    }

    @Test
    void getAllTickets() {
        List<Ticket> ticketList = ticketService.getAllTickets();
        assertFalse(ticketList.isEmpty());
    }

    @Test
    void updateTicket() {
        Ticket ticket1 = ticketService.getTicketById(new Id<>(5));
        Ticket ticket2 = new Ticket(ticket1.getId(), new Id(2), new Id(2), localDateTime, new Price(new BigDecimal(11)));
        ticketService.updateTicket(ticket2);

        assertEquals(ticket2.getRoomName(), ticket1.getRoomName());
        assertNotEquals(ticket2.getPrice(), ticket1.getPrice());
        assertEquals(ticket2.getId(), ticket1.getId());
    }


    @Test
    void deleteTicket() {
        List<Ticket> ticketList = ticketService.getAllTickets();
        Ticket ticket1 = ticketService.getTicketById(new Id<>(4));
        ticketService.deleteTicket(new Id(4));

        assertFalse(ticketList.contains(ticket1));
    }
}