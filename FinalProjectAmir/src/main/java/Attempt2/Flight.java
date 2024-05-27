package Attempt2;

public class Flight {
    private String origin;
    private String destination;
    private String date;
    private String flightNumber;
    private int maxPeopleCapacity;
    private int maxWeightCapacity;
    private boolean flightStatus;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getMaxPeopleCapacity() {
        return maxPeopleCapacity;
    }

    public void setMaxPeopleCapacity(int maxPeopleCapacity) {
        this.maxPeopleCapacity = maxPeopleCapacity;
    }

    public int getMaxWeightCapacity() {
        return maxWeightCapacity;
    }

    public void setMaxWeightCapacity(int maxWeightCapacity) {
        this.maxWeightCapacity = maxWeightCapacity;
    }

    public boolean getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(boolean flightStatus) {
        this.flightStatus = flightStatus;
    }

    public Flight(String origin, String destination, String date, String flightNumber, int maxPeopleCapacity, int maxWeightCapacity, boolean flightStatus) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.flightNumber = flightNumber;
        this.maxPeopleCapacity = maxPeopleCapacity;
        this.maxWeightCapacity = maxWeightCapacity;
        this.flightStatus = flightStatus;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", maxPeopleCapacity=" + maxPeopleCapacity +
                ", maxWeightCapacity=" + maxWeightCapacity +
                '}';
    }

    public boolean equals(Flight other) {
        if(this.getDestination().equals(other.getDestination()) &&
                this.getOrigin().equals(other.getOrigin()) &&
                this.getDate().equals(other.getDate())
        ){
            return true;
        }
        return false;
    }
}
