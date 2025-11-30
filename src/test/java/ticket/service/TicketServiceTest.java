package ticket.service;

import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import org.junit.jupiter.api.Test;
import ticket.dao.TicketDaoImpl;
import ticket.model.Ticket;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

class TicketServiceTest {
    LocalDateTime localDateTime = LocalDateTime.now();

    @Test
    void createTicket() {
        Ticket ticket1 = new Ticket(new Name("player1"), new Name("room1"),localDateTime, new BigDecimal(100));

        TicketDaoImpl ticketDaoImp = new TicketDaoImpl();
        TicketService ticketService = new TicketService(ticketDaoImp);
        ticketService.createTicket(ticket1);
        assertEquals(new Name("player1"), ticket1.getPlayerName());

    }

    @Test
    void getTicketById() {
        TicketDaoImpl ticketDaoImp = new TicketDaoImpl();
        TicketService ticketService = new TicketService(ticketDaoImp);
     //   Ticket ticket1 = new Ticket(new Name("player1"), new Name("room1"),localDateTime, new BigDecimal(100));

        Ticket ticket2 = ticketService.getTicketById(new Id<>(5));

        assertTrue( ticket2.getId().getValue() > 0);
    }

    @Test
    void getAllTickets() {
    }

    @Test
    void updateTicket() {
    }

    @Test
    void deleteTicket() {
    }
}