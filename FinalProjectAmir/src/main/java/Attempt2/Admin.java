package Attempt2;

public class Admin {
    private String userName;
    private String password;

    private FlightManager flightManager;
    private UserManager userManager;

    public Admin(String userName, String password, FlightManager flightManager, UserManager userManager) {
        this.userName = userName;
        this.password = password;
        this.flightManager = flightManager;
        this.userManager = userManager;
    }

    public Admin(String userName, String password) {
        this.userName = userName;
        this.password = password;
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

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
