package Attempt2;

import java.io.*;
import java.util.ArrayList;

public class User  {

    private FlightManager flightManager;
    private String userName;
    private String password;
    private ArrayList<Flight> bookedFlights = new ArrayList<>();


    public ArrayList<Flight> getBookedFlights() {
        return bookedFlights;
    }

    public void setBookedFlights(ArrayList<Flight> bookedFlights) {
        this.bookedFlights = bookedFlights;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
            this.password = password;
        }

    public FlightManager getFlightManager() {
        return flightManager;
    }

    public void setFlightManager(FlightManager flightManager) {
        this.flightManager = flightManager;
    }

    public User(String userName, String password, FlightManager flightManager) {
        this.userName = userName;
        this.password = password;
        this.flightManager = flightManager;
    }

    public boolean searchBookedFlights(Flight searchedFlight) {
        for(Flight flight: bookedFlights){
            if(flight.equals(searchedFlight)){
                return true;
            }
        }
        return false;
    }

    public boolean reserveFlight(Flight reservation) {
        if(flightManager.searchFlight(reservation) && !(searchBookedFlights(reservation))){
            bookedFlights.add(reservation);
            return true;
        }
        return false;
    }

    public boolean equals(User other) {
        if (this.getUserName().equals(other.getUserName()) && this.getPassword().equals(other.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", bookedFlights=" + bookedFlights;
    }
}
