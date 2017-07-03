package nl.avans.film_app;

public class Config {

    //Pak dit adres als de server local hosted is
    //public static final String SERVER_ADDRESS = "http://10.0.2.2:3000";
    //Pak dit adres als de server op heroku staat
    public static final String SERVER_ADDRESS = "http://film-serverheroku.herokuapp.com";

    public static final String URL_FILMS = SERVER_ADDRESS + "/api/v1/films";
    public static final String URL_RENTALS = SERVER_ADDRESS + "/api/v1/films/rentals/";
}