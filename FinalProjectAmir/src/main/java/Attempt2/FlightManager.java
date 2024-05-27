package Attempt2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class FlightManager implements IOManager {
    private ArrayList<Flight> flights;

    public FlightManager(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public FlightManager() {
        this.flights = new ArrayList<>();
    }

    public void addFlight(Flight flight){
        if (!searchFlight(flight)) {
            flights.add(flight);
            bubbleSort(flights);
        }
    }
    public void deleteFlight(Flight flight){
        if (searchFlight(flight)) {
            flights.remove(flight);
        }
    }

    public boolean searchFlight(String origin, String destination, String date) {
        for (Flight flight : flights) {
            if (flight.getOrigin().toLowerCase(Locale.ROOT).equals(origin.toLowerCase(Locale.ROOT)) &&
                flight.getDestination().toLowerCase(Locale.ROOT).equals(destination.toLowerCase(Locale.ROOT)) &&
                flight.getDate().equals(date)) {

                return true;
            }
        }
        return false;
    }

    public  Flight searchFlightByOriginDestinationDate(String origin, String destination, String date) {
        for (Flight flight : flights) {
            if (flight.getOrigin().toLowerCase(Locale.ROOT).equals(origin.toLowerCase(Locale.ROOT)) &&
                    flight.getDestination().toLowerCase(Locale.ROOT).equals(destination.toLowerCase(Locale.ROOT)) &&
                    flight.getDate().equals(date)) {
                return flight;
            }
        }

        return null;
    }

    public boolean searchFlight(Flight searchedFlight) {
        for(Flight flight: flights){
            if(flight.equals(searchedFlight)
            ){
                return true;
            }
        }
        return false;
    }

    public boolean searchFlight(String searchedFlightId) {
        for(Flight flight: flights){
            if(flight.getFlightNumber().equals(searchedFlightId)
            ){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public Flight getFlightById(String flightId){
        int indexOfFlight = binarySearch(flights, flightId);

        if (indexOfFlight == -1) {
            return null;
        } else {
            return flights.get(indexOfFlight);
        }
    }

    public  int binarySearch(ArrayList<Flight> flights, String key) {
        int low = 0;
        int high = flights.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            String midVal = flights.get(mid).getFlightNumber();

            int compare = midVal.compareTo(key);

            if (compare < 0) {
                low = mid + 1;
            } else if (compare > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public void bubbleSort(ArrayList<Flight> flights) {
        int n = flights.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (flights.get(j).getFlightNumber().compareTo(flights.get(j + 1).getFlightNumber()) > 0) {
                    Flight temp = flights.get(j);
                    flights.set(j, flights.get(j + 1));
                    flights.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    @Override
    public void loadFromFile(FileReader reader) {

        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            String[] stringParams = new String [7] ;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(";") ){
                    stringParams = line.split(";");
                    Flight flight = new Flight(stringParams[0], stringParams[1],
                            stringParams[2], stringParams[3],
                            Integer.valueOf(stringParams[4]),
                            Integer.valueOf(stringParams[5]), Boolean.parseBoolean(stringParams[6]));
                    addFlight(flight);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveToFile(FileWriter flightFile) {
        try {
            for(Flight flight : flights){
                flightFile.write(
                        flight.getOrigin() + ";" +
                                flight.getDestination() + ";" +
                                flight.getDate() + ";" +
                                flight.getFlightNumber() + ";" +
                                flight.getMaxPeopleCapacity() + ";" +
                                flight.getMaxWeightCapacity() + ";" +
                                flight.getFlightStatus() +  '\n'
                );
            }

            flightFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "FlightManager{" +
                "flights=" + flights +
                '}';
    }
}
