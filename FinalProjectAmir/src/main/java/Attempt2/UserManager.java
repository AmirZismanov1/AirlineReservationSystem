package Attempt2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserManager implements IOManager {
    private ArrayList<User> users;
    public void addUser(User user){
        if (!searchUser(user)) {
            users.add(user);
        }
    }

    public boolean searchUser(User user){
        for (User user1 : users) {
            if (user1.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public boolean searchUser(String username){
        for (User user1 : users) {
            if (user1.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean searchUserForFLight(User user, String origin, String destination, String date){
        if (user.searchBookedFlights(user.getFlightManager().searchFlightByOriginDestinationDate(origin, destination, date))) {
            return true;
        }
        return false;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void addUserToFlight(User user, FlightManager flightManager, String origin, String destination, String date){
        if (!searchUserForFLight(user, origin, destination, date)) {
            Flight flight = flightManager.searchFlightByOriginDestinationDate(origin, destination, date);
            ArrayList<Flight> bookedFlights = user.getBookedFlights();
            bookedFlights.add(flight);
            user.setBookedFlights(bookedFlights);
        }
    }

    public void deleteUserFromFlight(User user, FlightManager flightManager, String origin, String destination, String date){
        if (searchUserForFLight(user, origin, destination, date)) {
            Flight flight = flightManager.searchFlightByOriginDestinationDate(origin, destination, date);
            user.getBookedFlights().remove(flight);
        }
    }

    public UserManager(ArrayList<User> users) {
        this.users = users;
    }

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public User getUserByNameAndPassword (String username, String password) {

        for (User user : users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static void insertionSort(ArrayList<User> users) {
        int n = users.size();
        for (int i = 1; i < n; i++) {
            User currentUser = users.get(i);
            int j = i - 1;
            while (j >= 0 && users.get(j).getUserName().compareTo(currentUser.getUserName()) > 0) {
                users.set(j + 1, users.get(j));
                j = j - 1;
            }
            users.set(j + 1, currentUser);
        }
    }

    @Override
    public void loadFromFile(FileReader reader) {
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            String[] stringParams = new String [4];
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(";")) {
                    stringParams = line.split(";");
                    User user = ( new User(
                            stringParams[0], stringParams[1], Main.FLIGHTMANAGER)

                    );

                    if (stringParams.length > 2 && stringParams[2].contains(":")) {
                        String [] flightNumbers = stringParams[2].split(":");
                        for (String flightId : flightNumbers) {
                            if (Main.FLIGHTMANAGER.searchFlight(flightId)) {
                                user.reserveFlight(Main.FLIGHTMANAGER.getFlightById(flightId));
                            }
                        }

                    }
                    users.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveToFile(FileWriter userFile) {
        insertionSort(users);
        try {
            for (User user : users) {
                StringBuilder userFlights = new StringBuilder();
                for (Flight flight : user.getBookedFlights()) {
                    userFlights.append(flight.getFlightNumber()).append(":");
                }
                userFile.write(user.getUserName() + ";" + user.getPassword() + ";" + userFlights.toString() + "\n");
            }
            userFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
