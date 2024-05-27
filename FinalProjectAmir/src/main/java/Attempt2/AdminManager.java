package Attempt2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AdminManager implements IOManager {
    private ArrayList<Admin> admins;

    public AdminManager(ArrayList<Admin> admins) {
        this.admins = admins;
    }

    public AdminManager() {
        this.admins = new ArrayList<>();
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
    }

    public Admin getAdminByNameAndPassword (String username, String password) {
        for (Admin admin : admins) {
            if (admin.getUserName().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }

    public Flight createNewFlight (String origin, String destination, String date, String flightNumber, int maxPeopleCapacity, int maxWeightCapacity, boolean flightStatus) {
        Flight flight = new Flight(origin, destination, date, flightNumber, maxPeopleCapacity, maxWeightCapacity, flightStatus);
        return flight;
    }

    public void loadFromFile(FileReader reader) {
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            String[] stringParams = new String [2] ;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(";") ){
                    stringParams = line.split(";");
                    Admin admin = new Admin(stringParams[0], stringParams[1]);
                    admins.add(admin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveToFile(FileWriter flightFile) {
        String userFlightNumber = "";
        try {
            for(Admin admin : admins){
                flightFile.write(
                        admin.getUserName() + ";" + admin.getPassword() + ";" +  '\n'
                );
            }

            flightFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
