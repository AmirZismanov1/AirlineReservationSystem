package Attempt2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TicketManager implements IOManager{
    ArrayList<Ticket> tickets = new ArrayList<>();

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public Ticket searchForTicket(String origin, String destination, String userName, String password) {
        for (Ticket ticket : tickets) {
            if (ticket.getOrigin().equals(origin) &&
                    ticket.getDestination().equals(destination) &&
                    ticket.getUserName().equals(userName) &&
                    ticket.getPassword().equals(password)) {
                return ticket;
            }
        }
        return null;
    }

    public Ticket searchForTicketByFlightNumber(String flightNumber, String userName, String password) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketFlightNumber().equals(flightNumber)  &&
                    ticket.getUserName().equals(userName) &&
                    ticket.getPassword().equals(password)) {
                return ticket;
            }
        }
        return null;
    }

    public economyTicket createNewEconomyForUser( String origin, String destination, String ticketNumber, String userName, String password, String ticketFlightNumber, String ticketStatus) {
    economyTicket economyTicket = new economyTicket (origin, destination, ticketNumber, userName, password, ticketFlightNumber, ticketStatus);
        return economyTicket;
    }

    public businessTicket createNewBusinessClassForUser(String origin, String destination, String ticketNumber, String userName, String password, String ticketFlightNumber, String ticketStatus) {
        businessTicket businessTicket = new businessTicket(origin, destination, ticketNumber, userName, password, ticketFlightNumber, ticketStatus);
        addTicket(businessTicket);
        return businessTicket;
    }

    public firstClassTicket createNewFirstClassForUser(String origin, String destination, String ticketNumber, String userName, String password, String ticketFlightNumber, String ticketStatus) {
        firstClassTicket firstClassTicket = new firstClassTicket(origin, destination, ticketNumber, userName, password, ticketFlightNumber, ticketStatus);
        addTicket(firstClassTicket);
        return firstClassTicket;
    }

    public boolean cancelTicket(Ticket ticket) {
        if (ticket instanceof Cancellable) {
            Cancellable cancellable = (Cancellable) ticket;
            if (cancellable.toCancel()) {
                tickets.remove(ticket);
                return true;
            }
        }
        return false;
    }

    @Override
    public void loadFromFile(FileReader reader) {
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            String[] stringParams = new String [8];
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(";")) {
                    stringParams = line.split(";");

                    if (Integer.valueOf(stringParams[0]) == 300) {
                        tickets.add(new economyTicket(stringParams[1], stringParams[2], stringParams[3], stringParams[4], stringParams[5], stringParams[6], stringParams[7]));
                    } else if (Integer.valueOf(stringParams[0]) == 600) {
                        tickets.add(new businessTicket(stringParams[1], stringParams[2], stringParams[3], stringParams[4], stringParams[5],stringParams[6], stringParams[7]));
                    } else {
                        tickets.add(new firstClassTicket(stringParams[1], stringParams[2], stringParams[3], stringParams[4], stringParams[5], stringParams[6], stringParams[7]));
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveToFile(FileWriter ticketFile) {
        try {
            for (Ticket ticket : tickets) {
                ticketFile.write(ticket.getPrice() + ";" + ticket.getOrigin() + ";" + ticket.getDestination() + ";" + ticket.getTicketNumber() + ";" + ticket.getUserName() + ";" + ticket.getPassword() + ";" + ticket.getTicketFlightNumber() + ";" + ticket.getTicketStatus() +  "\n");
            }
            ticketFile.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
