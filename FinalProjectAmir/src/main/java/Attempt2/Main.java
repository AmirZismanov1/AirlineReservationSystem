package Attempt2;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static final FlightManager FLIGHTMANAGER = new FlightManager();
    public static final UserManager USERMANAGER = new UserManager();
    public static final AdminManager ADMINMANAGER = new AdminManager();
    public static final TicketManager TICKETMANAGER = new TicketManager();

    public static void main(String[] args) {
        loadData();
        homePage();
        saveData();
    }

    public static void loadData() {
        try {
            FileReader reader1 = new FileReader("flightInfo.txt");
            FLIGHTMANAGER.loadFromFile(reader1);

            FileReader reader2 = new FileReader("userInfo.txt");
            USERMANAGER.loadFromFile(reader2);

            FileReader reader3 = new FileReader("adminInfo.txt");
            ADMINMANAGER.loadFromFile(reader3);

            FileReader reader4 = new FileReader("ticketInfo.txt");
            TICKETMANAGER.loadFromFile(reader4);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveData() {
        try {FileWriter flightFileWriter = new FileWriter("flightInfo.txt", false);
            FLIGHTMANAGER.saveToFile(flightFileWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {FileWriter userFileWriter = new FileWriter("userInfo.txt", false);
            USERMANAGER.saveToFile(userFileWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {FileWriter adminFileWriter = new FileWriter("adminInfo.txt", false);
            ADMINMANAGER.saveToFile(adminFileWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {FileWriter ticketFileWriter = new FileWriter("ticketInfo.txt", false);
            TICKETMANAGER.saveToFile(ticketFileWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void homePage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the TicketReserve");
        System.out.println("If you wish to create a new account please wright 'Create'");
        System.out.println("If you wish to login please wright 'Login'");
        System.out.println("If you are an admin please wright 'Admin'");
        System.out.println("If you wish to completely exist the program please write 'Quit'");
        String userInput;
        boolean toQuit = false;

        try {
            do {
                userInput = scanner.next();
                if (userInput.toLowerCase(Locale.ROOT).equals("create")) {
                    createNewAccount();
                    break;
                }
                if (userInput.toLowerCase(Locale.ROOT).equals("login")) {
                    login();
                    break;
                }
                if (userInput.toLowerCase(Locale.ROOT).equals("admin")) {
                    adminLogin();
                    break;
                }
                if (userInput.toLowerCase(Locale.ROOT).equals("quit")) {
                    System.out.println("Thank you for using TicketReserve");
                    toQuit = true;
                    break;
                }
                else {
                    System.out.println("Please enter a valid input");
                }
            } while (!userInput.toLowerCase(Locale.ROOT).equals("create") &&
                    !userInput.toLowerCase(Locale.ROOT).equals("login") &&
                    !userInput.toLowerCase(Locale.ROOT).equals("admin") &&
                    !userInput.toLowerCase(Locale.ROOT).equals("quit") && !toQuit);

        } catch (Exception e) {
            System.out.println(e);
            homePage();
        }
    }

    private static void adminLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String userName = scanner.next();

        System.out.println("Enter your password: ");
        String password = scanner.next();

        Admin admin = ADMINMANAGER.getAdminByNameAndPassword(userName, password);

        if (admin == null) {
            System.out.println("You are not the admin. You will be redirected to the home page");
            homePage();
        } else {
            System.out.println("Welcome to your account " + admin.getUserName());
            alreadyLoggedInAdmin(admin);
        }
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String userName = scanner.next();

        System.out.println("Enter your password: ");
        String password = scanner.next();

        User loggedInUser = (USERMANAGER.getUserByNameAndPassword(userName, password));

        if (loggedInUser == null) {
            System.out.println("This account does not exist. You will be redirected to the home page where you can create a new account");
            homePage();
        } else {
            System.out.println("Welcome to your account " + loggedInUser.getUserName());
            alreadyLoggedIn(loggedInUser);
        }
    }

    public static void createNewAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username: ");
        String newUsername = scanner.next();
        System.out.println("Enter a password: ");
        String newPassword = scanner.next();

        if (USERMANAGER.searchUser(newUsername)) {
            System.out.println("This user already exists. You will be redirected to the main menu");
            homePage();
        } else {

            User user = new User(newUsername, newPassword, FLIGHTMANAGER);

            USERMANAGER.addUser(user);
            System.out.println("Your account has been created successfully");

            alreadyLoggedIn(user);
        }
    }

    private static void alreadyLoggedInAdmin(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        boolean toContinue = true;

        while (toContinue) {
            System.out.println("\nWhat do you wish to do (select a number): ");
            System.out.println("1.Add a flight");
            System.out.println("2.Modify a flight");
            System.out.println("3.View all users");
            System.out.println("4.View all flights");
            System.out.println("5.View a specific flight with all details");
            System.out.println("6.View a specific user with all details");
            System.out.println("7.View all bookings (reports)");
            System.out.println("8.Process a booking");
            System.out.println("9.Logout");

            int adminChoice = scanner.nextInt();

            switch (adminChoice) {
                case 1:
                    addFlight();
                    break;
                case 2:
                    rescheduleFlight();
                    break;
                case 3:
                    viewUsers();
                    break;
                case 4:
                    viewFlights();
                    break;
                case 5:
                    viewSpecificFlight(admin);
                    break;
                case 6:
                    viewSpecificUser(admin);
                    break;

                case 7:
                    boolean onlyView = true;
                    viewAndProcessBookings(onlyView);
                    break;

                case 8:
                    onlyView = false;
                    viewAndProcessBookings(onlyView);
                    break;
                case 9:
                    System.out.println("Thank you for using TicketReserve. We are logging you out.");
                    homePage();
                    toContinue = false;
                    break;
            }
        }
    }

    private static void viewAndProcessBookings(boolean onlyView) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Here is a list of all the current bookings (reports)");
        boolean validChoice = true;

        for (int i = 0; i < TICKETMANAGER.tickets.size(); i++) {
            System.out.println((i + 1) + ": " + TICKETMANAGER.tickets.get(i));
        }

        if (!onlyView) {
            while (validChoice) {
                System.out.println("Enter the number of the ticket you want to process: ");
                int choice = scanner.nextInt();

                if (choice > 0 && choice <= TICKETMANAGER.tickets.size()) {
                    Ticket selectedTicket = TICKETMANAGER.tickets.get(choice - 1);
                    System.out.println("Do you wish to confirm or deny the booking: ");

                    scanner.nextLine();
                    String adminChoice = scanner.nextLine();

                    if (adminChoice.toLowerCase(Locale.ROOT).equals("confirm")) {
                        selectedTicket.setTicketStatus("approved");
                        System.out.println("Approved successfully");
                        validChoice = false;
                    } else if (adminChoice.toLowerCase(Locale.ROOT).equals("deny")) {
                        selectedTicket.setTicketStatus("denied");
                        System.out.println("Denied successfully");
                        validChoice = false;
                    }

                } else {
                    System.out.println("Invalid choice. Please try again.");
                }

            }
        }
    }

    private static void addFlight() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter departure city:");
        String origin = scanner.nextLine();
        System.out.println("Enter destination city:");
        String destination = scanner.nextLine();
        System.out.println("Enter date (YYYY-MM-DD):");
        String date = scanner.nextLine();

        if (FLIGHTMANAGER.searchFlight(origin, destination, date)) {
            System.out.println("This flight already exists. You will be redirected to the main menu");
        } else {
            System.out.println("Enter the flight number:");
            String flightNumber = scanner.nextLine();
            System.out.println("Enter the maximum people capacity:");
            int maxPeople = scanner.nextInt();
            System.out.println("Enter the maximum weight capacity (in kg):");
            int maxWeight = scanner.nextInt();
            boolean flightStatus = true;

            Flight newFlight =  ADMINMANAGER.createNewFlight(origin, destination, date, flightNumber, maxPeople, maxWeight, flightStatus);
            FLIGHTMANAGER.addFlight(newFlight);
            System.out.println("Flight added successfully");
        }
    }

    public static void rescheduleFlight() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the information of the flight you want to modify");
        System.out.println("Enter departure city:");
        String origin = scanner.nextLine();
        System.out.println("Enter destination city:");
        String destination = scanner.nextLine();
        System.out.println("Enter date (YYYY-MM-DD):");
        String date = scanner.nextLine();

        if (FLIGHTMANAGER.searchFlight(origin, destination, date)) {
            System.out.println("Enter the new information");
            System.out.println("Enter departure city:");
            String newOrigin = scanner.nextLine();
            System.out.println("Enter destination city:");
            String newDestination = scanner.nextLine();
            System.out.println("Enter date (YYYY-MM-DD):");
            String newDate = scanner.nextLine();
            System.out.println("Enter the flight number:");
            String newFlightNumber = scanner.nextLine();
            System.out.println("Enter the maximum people capacity:");
            int newMaxPeople = scanner.nextInt();
            System.out.println("Enter the maximum weight capacity:");
            int newMaxWeight = scanner.nextInt();
            boolean newFlightStatus = true;

           Flight oldFlight =  FLIGHTMANAGER.searchFlightByOriginDestinationDate(origin, destination, date);
           FLIGHTMANAGER.deleteFlight(oldFlight);
           Flight newFlight = ADMINMANAGER.createNewFlight(newOrigin, newDestination, newDate, newFlightNumber, newMaxPeople, newMaxWeight, newFlightStatus);
           FLIGHTMANAGER.addFlight(newFlight);
            System.out.println("Flight modified successfully");
        } else {
            System.out.println("This flight does not exist. It is impossible to modify it. You will be redirected to the main menu");
        }
    }

    private static void viewUsers() {
        int display = 1;
        for (User user : USERMANAGER.getUsers()) {
            System.out.println(display + ": " + user.toString());
            display++;
        }

    }

    private static void viewFlights() {
        int display = 1;
        for (Flight flight : FLIGHTMANAGER.getFlights()) {
            System.out.println(display + ": " + flight.toString());
            display++;
        }
    }

    private static void viewSpecificUser(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username: ");
        String username = scanner.next();
        System.out.println("Enter a password: ");
        String password = scanner.next();

        User loggedInUser = (USERMANAGER.getUserByNameAndPassword(username, password));

        if (loggedInUser == null) {
            System.out.println("This account does not exist. You will be redirected to the main menu");
            alreadyLoggedInAdmin(admin);
        } else {
            System.out.println("Here is the information about the wanted user: ");
            System.out.println(loggedInUser);
        }
    }

    private static void viewSpecificFlight(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the information about your requested flight");
        System.out.println("Enter departure city:");
        String origin = scanner.nextLine();
        System.out.println("Enter destination city:");
        String destination = scanner.nextLine();
        System.out.println("Enter date (YYYY-MM-DD):");
        String date = scanner.nextLine();

        if (FLIGHTMANAGER.searchFlight(origin, destination, date)) {
            Flight specificFlight = FLIGHTMANAGER.searchFlightByOriginDestinationDate(origin, destination, date);
            System.out.println("Here is the specific flight you requested: ");
            System.out.println(specificFlight);
        } else {
            System.out.println("This flight does not exist. You will be redirected to the main menu");
            alreadyLoggedInAdmin(admin);
        }
    }

    public static void alreadyLoggedIn(User user) {
        Scanner scanner = new Scanner(System.in);
        boolean toContinue = true;

        while (toContinue) {
            System.out.println("\nWhat do you wish to do(select a number): ");
            System.out.println("1. Search for flights");
            System.out.println("2. Reserve a flight");
            System.out.println("3. Change flight reservation");
            System.out.println("4. Delete flight reservation");
            System.out.println("5. View your flight reservations");
            System.out.println("6. View all available flights");
            System.out.println("7. Log out");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayFlights();
                    break;

                case 2:
                    reserveFlight(user);
                    break;

                case 3:
                    changeFlightReservation(user);
                    break;

                case 4:
                    deleteFlightReservation(user);
                    break;

                case 5:
                    viewUserFlights(user);
                    break;

                case 6:
                    displayAvailableFlightsUser();
                    break;

                case 7:
                    System.out.println("Thank you for using TicketReserve. We are logging you out");
                    toContinue = false;
                    homePage();
                    break;

                default:
                    System.out.println("Invalid input");
            }
        }
    }

    private static void displayFlights() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter departure city:");
        String origin = scanner.nextLine();
        System.out.println("Enter destination city:");
        String destination = scanner.nextLine();
        System.out.println("Enter date (YYYY-MM-DD):");
        String date = scanner.nextLine();


        if (FLIGHTMANAGER.searchFlight(origin, destination, date)) {
            Flight flight = FLIGHTMANAGER.searchFlightByOriginDestinationDate(origin, destination, date);
            System.out.println("Here are the available flights");
            System.out.println(flight.getOrigin() + ", " + flight.getDestination() + ", " + flight.getDate());
        } else {
            System.out.println("No flights are available");
        }
    }

    private static void reserveFlight(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter departure city:");
        String departureCity = scanner.nextLine();
        System.out.println("Enter destination city:");
        String destinationCity = scanner.nextLine();
        System.out.println("Enter date (YYYY-MM-DD):");
        String dateOfFlight = scanner.nextLine();

        if (FLIGHTMANAGER.searchFlight(departureCity, destinationCity, dateOfFlight)) {
            if (!USERMANAGER.searchUserForFLight(user, departureCity, destinationCity, dateOfFlight)) {
                ticketTypeReservation(user, departureCity, destinationCity, dateOfFlight);
                USERMANAGER.addUserToFlight(user, FLIGHTMANAGER, departureCity, destinationCity, dateOfFlight);
                System.out.println("Flight reserved successfully");
            } else {
                System.out.println("You are already registered on this flight. You will be redirected back to the main menu");
            }

        } else {
            System.out.println("This flight is not available. You will be redirected back to the main menu");
        }
    }

    private static void ticketTypeReservation(User user, String departureCity, String destinationCity, String dateOfFlight) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What ticket do you wish to choose? Please type in the number of the ticket: ");
        System.out.println("1. Economy ticket -> 300$");
        System.out.println("2. Business class ticket -> 600$");
        System.out.println("3. First class ticket -> 1000$");
        int userChoiceTicket = scanner.nextInt();

        String flightNumber = FLIGHTMANAGER.searchFlightByOriginDestinationDate(departureCity, destinationCity, dateOfFlight).getFlightNumber();
        String ticketNumber = flightNumber + user.getUserName() + "";
        String ticketStatus = "pending";

        switch (userChoiceTicket) {
            case 1:
                TICKETMANAGER.createNewEconomyForUser(departureCity, destinationCity,ticketNumber, user.getUserName(), user.getPassword(),flightNumber, ticketStatus);
                break;
            case 2:
                TICKETMANAGER.createNewBusinessClassForUser(departureCity, destinationCity,ticketNumber, user.getUserName(), user.getPassword(),flightNumber, ticketStatus);
                break;
            case 3:
                TICKETMANAGER.createNewFirstClassForUser(departureCity, destinationCity,ticketNumber, user.getUserName(), user.getPassword(),flightNumber, ticketStatus);
                break;

            default:
                System.out.println("This is not a valid input. You will be redirected to reserving a ticket again: ");
                reserveFlight(user);
                break;
        }
    }

    private static void changeFlightReservation(User user) {
        if (user.getBookedFlights().size() == 0) {
            System.out.println("You have no booked flights. You will be redirected to the main menu");
        } else {
            if (deleteFlightReservation(user)) {
                System.out.println("Please enter the information about your new desired flight");
                reserveFlight(user);
            }
        }
    }

    private static boolean deleteFlightReservation(User user) {
        if (user.getBookedFlights().size() == 0) {
            System.out.println("You have no booked flights. You will be redirected to the main menu");
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the flight you wish to have removed");
            System.out.println("Enter departure city:");
            String departureCity = scanner.nextLine();
            System.out.println("Enter destination city:");
            String destinationCity = scanner.nextLine();
            System.out.println("Enter date (YYYY-MM-DD):");
            String dateOfFlight = scanner.nextLine();

            Ticket ticket = TICKETMANAGER.searchForTicket(departureCity, destinationCity, user.getUserName(), user.getPassword());

            if (TICKETMANAGER.cancelTicket(ticket)) {
                System.out.println("We can cancel your ticket. ");
                if (USERMANAGER.searchUserForFLight(user,departureCity, destinationCity, dateOfFlight)) {
                    USERMANAGER.deleteUserFromFlight(user, FLIGHTMANAGER, departureCity, destinationCity,dateOfFlight);
                    TICKETMANAGER.cancelTicket(ticket);
                    System.out.println("Flight reservation deleted successfully");
                    return true;
                }
                else {
                    System.out.println("You do not have a reservation on this flight. You will be redirected to the main menu");
                    alreadyLoggedIn(user);
                }
            } else {
                System.out.println("The economy ticket which you purchased does not allow to cancel or modify the flight reservation");
                return false;
            }

        }
        return false;
    }

    private static void viewUserFlights(User user) {
        if (user.getBookedFlights().size() == 0) {
            System.out.println("You are not registered to any flights");
        } else {
            System.out.println("Here are the flights you currently reserved: ");
            int display = 1;
            for (Flight flight : user.getBookedFlights()) {
                String flightNumber = flight.getFlightNumber();
                Ticket ticketType = TICKETMANAGER.searchForTicketByFlightNumber(flightNumber, user.getUserName(), user.getPassword());
                String ticketClass = "";

                if (ticketType == null) {

                } else {

                    if (ticketType.getPrice() == 300) {
                        ticketClass = "Economy ticket";
                    }

                    if (ticketType.getPrice() == 600) {
                        ticketClass = "Business class ticket";
                    }

                    if (ticketType.getPrice() == 1000) {
                        ticketClass = "First class ticket";
                    }
                    System.out.println(display + ": " + flight.getOrigin() + ", " + flight.getDestination() + ", " + flight.getDate() + ", " + ticketClass);
                    display++;
                }
            }
        }
    }

    public static void displayAvailableFlightsUser() {
        if (FLIGHTMANAGER.getFlights().size() != 0) {
            System.out.println("Here are the available flights: ");
            int display = 1;
            for (Flight flight : FLIGHTMANAGER.getFlights()) {
                System.out.println(display + ": " + flight.getOrigin() + ", " + flight.getDestination() + ", " + flight.getDate());
                display++;
            }
        } else {
            System.out.println("There are no available flights");
        }
    }
}
