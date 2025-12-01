package menu;

import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import player.dao.PlayerDaoImpl;
import player.service.PlayerService;
import room.dao.RoomDaoImpl;
import room.service.RoomService;
import ticket.dao.TicketDaoImpl;
import ticket.model.Ticket;
import ticket.service.TicketService;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class TicketMenuControler extends BaseMenuController {
    private final TicketService ticketService;
    private final TicketDaoImpl ticketDaoImplementation = new TicketDaoImpl();

    public TicketMenuControler(Scanner scanner) {
        super(scanner);
        this.ticketService = new TicketService(ticketDaoImplementation);
    }

    public void showTicketMenu() {
        int option;

        do {
            MenuPrinter.displayTicketMenu();
            option = askOption("Choose an option (1-3): ", 1, 3);

            switch (option) {
                case 1:
                    handleCreateTicket();
                    break;
                case 2:
                    handleListTIcket();
                    break;
                case 3:
                    out.println("\nReturning to previous menu...");
                    break;
            }

        } while (option != 3);
    }

    private void handleCreateTicket() {
        out.println("\n--- Create new Ticket ---");

        try {
            out.println("Please enter the nickname of the player.");
            Name playerNickname = ConsoleInputReader.readName(scanner, "player nickname");
            out.println("Please enter the name of the room.");
            Name roomName = ConsoleInputReader.readName(scanner, "room name");

            RoomDaoImpl roomDao = new RoomDaoImpl();
            RoomService roomService = new RoomService(roomDao);

            Id roomId = roomService.getIdByName(roomName);
            Price price = roomService.getTotalRoomPrice(roomId);

            Ticket ticket = new Ticket(playerNickname, roomName, price.toBigDecimal());
            ticketService.createTicketMinimumValues(ticket);


        } catch (InputReadException e) {
            out.println("\nERROR: " + e.getMessage());
        } catch (Exception e) {
            out.println("\nUnexpected error while creating the Ticket.\n" + e.getMessage());
        }
    }

    private void handleListTIcket() {
        out.println("\n--- List of Tickets ---");

        try {
            List<Ticket> ticketList = ticketService.getAllTickets();

            if (ticketList == null || ticketList.isEmpty()) {
                out.println("There are no tickets registered yet.");
                return;
            }
            for (Ticket ticket : ticketList) {
                out.println("------------------------------");

                RoomDaoImpl roomDao = new RoomDaoImpl();
                RoomService roomService = new RoomService(roomDao);
                Name roomName = roomService.getNameById(ticket.getRoomId());

                PlayerDaoImpl playerDao = new PlayerDaoImpl();
                PlayerService playerService = new PlayerService(playerDao);
                Name playerName = playerService.findNameById(ticket.getPlayerId());
                out.println("Player nickname: " + playerName);
                out.println("Room: " + roomName);
                out.println("Date: " + ticket.getLocalDateTime());
                out.println("Price: " + ticket.getPrice().toBigDecimal() + "€");
            }
            out.println("------------------------------");

        } catch (Exception e) {
            out.println("\nERROR retrieving Ticket list.");
        }
    }


    @Override
    public void showMenu() {
        showTicketMenu();
    }

}
