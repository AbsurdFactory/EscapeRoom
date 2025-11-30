package inventory;

import clue.dao.ClueDaoImplementation;
import clue.model.Clue;
import clue.service.ClueService;
import commonValueObjects.Price;
import objectdecoration.dao.ObjectDecorationDaoImpl;
import objectdecoration.model.ObjectDecoration;
import objectdecoration.service.ObjectDecorationService;
import room.dao.RoomDaoImpl;
import room.service.RoomService;
import ticket.dao.TicketDaoImpl;
import ticket.model.Ticket;
import ticket.service.TicketService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class InventoryService {

    public BigDecimal getCluesTotalPrice() {
        ClueService clueService = new ClueService(new ClueDaoImplementation());
        List<Clue> clueList = clueService.getAllClues();
        BigDecimal totalCluesPrice = BigDecimal.ZERO;

         totalCluesPrice = clueList.stream()
                .filter(Objects::nonNull)
                .map(Clue::getPrice)
                .map(Price::toBigDecimal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalCluesPrice;
    }

    public BigDecimal getObjectDecorationsTotalPrice() {
        ObjectDecorationService objectDecorationService = new ObjectDecorationService(new ObjectDecorationDaoImpl());
        List<ObjectDecoration> decorationsList = objectDecorationService.getAll();

        BigDecimal totalDecorationsPrice = BigDecimal.ZERO;

        totalDecorationsPrice = decorationsList.stream()
                .filter(Objects::nonNull)
                .map(ObjectDecoration::getPrice)
                .map(Price::toBigDecimal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalDecorationsPrice;
    }

    public BigDecimal getTotalValueOfInventory(){
        return  getCluesTotalPrice().add(getObjectDecorationsTotalPrice());
    }

    public int getAllItemsQuantitiesFromInventory(){
        int total= 0;
        total = getAllCluesFromInventory() + getAllObjectDecorationsFromInventory() + getAllRoomsFromInventory();
        return total;
    }

    public int getAllRoomsFromInventory(){
        int total= 0;
        RoomService roomService = new RoomService(new RoomDaoImpl());
        total = roomService.getAllRooms().size();
        return total;
    }

    public int getAllCluesFromInventory(){
        int total= 0;
        ClueService clueService = new ClueService(new ClueDaoImplementation());
        total = clueService.getAllClues().size();
        return total;
    }

    public int getAllObjectDecorationsFromInventory(){
        int total= 0;
        ObjectDecorationService objectDecorationService = new ObjectDecorationService(new ObjectDecorationDaoImpl());
        total = objectDecorationService.getAll().size();
        return total;
    }

    public BigDecimal calculateTotalIncome(){
        BigDecimal totalIncome =  BigDecimal.ZERO;

        TicketService inventoryService = new TicketService(new TicketDaoImpl());
        List <Ticket> ticketList = inventoryService.getAllTickets();
        ticketList.forEach(ticket -> ticket.getPrice());


        totalIncome = ticketList.stream()
                .filter(Objects::nonNull)
                .map(Ticket::getPrice)
                .map(Price::toBigDecimal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalIncome;
    }
}
