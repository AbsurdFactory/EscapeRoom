package ticket.service;

import commonValueObjects.Id;

import exceptions.DataAccessException;
import ticket.dao.TicketDaoImpl;
import ticket.model.Ticket;

import java.util.List;
import java.util.Optional;

public class TicketService {
    private final TicketDaoImpl ticketDaoImp;

    public TicketService(TicketDaoImpl ticketDao) {
        this.ticketDaoImp = ticketDao;

    }

    public void createTicket(Ticket ticket) {
        try {
            ticketDaoImp.save(ticket);
        } catch (Exception e) {
            throw new DataAccessException("Error saving ticket", e);
        }
    }

    public Ticket getTicketById(Id id) {
        try {
            return ticketDaoImp.findTicketById(id);
        } catch (Exception e) {
            throw new DataAccessException("Error retrieving ticket by ID", e);
        }
    }

    public List<Ticket> getAllTickets() {
        try {
            return ticketDaoImp.findAll();
        } catch (Exception e) {
            throw new DataAccessException("Error retrieving all tickets", e);
        }
    }

    public boolean updateTicket(Ticket ticket) {
        try {
            return ticketDaoImp.update(ticket);
        } catch (Exception e) {
            throw new DataAccessException("Error updating ticket", e);
        }
    }

    public boolean deleteTicket(Id id) {
        try {
            return ticketDaoImp.delete(id);
        } catch (Exception e) {
            throw new DataAccessException("Error deleting ticket", e);
        }
    }
}

