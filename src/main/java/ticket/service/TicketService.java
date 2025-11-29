package ticket.service;

import commonValueObjects.Id;
import ticket.dao.TicketDao;
import exceptions.DataAccessException;
import ticket.model.Ticket;

import java.util.List;
import java.util.Optional;

public class TicketService {

    private final TicketDao ticketDao;


    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;

    }

    public void createTicket(Ticket ticket) {
        try {
            ticketDao.save(ticket);
        } catch (Exception e) {
            throw new DataAccessException("Error saving ticket", e);
        }
    }

    public Optional<Ticket> getTicketById(Id id) {
        try {
            return ticketDao.findById(id);
        } catch (Exception e) {
            throw new DataAccessException("Error retrieving ticket by ID", e);
        }
    }

    public List<Ticket> getAllTickets() {
        try {
            return ticketDao.findAll();
        } catch (Exception e) {
            throw new DataAccessException("Error retrieving all tickets", e);
        }
    }

    public boolean updateTicket(Ticket ticket) {
        try {
            return ticketDao.update(ticket);
        } catch (Exception e) {
            throw new DataAccessException("Error updating ticket", e);
        }
    }

    public boolean deleteTicket(Id id) {
        try {
            return ticketDao.delete(id);
        } catch (Exception e) {
            throw new DataAccessException("Error deleting ticket", e);
        }
    }
}

