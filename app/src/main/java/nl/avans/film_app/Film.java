package nl.avans.film_app;

/**
 * Created by Daan on 18-6-2017.
 */

import java.io.Serializable;

public class Film implements Serializable {

    private int film_id, release_year,rental_duration, rental_rate, length, replacement_cost;
    private String title, description, rating, special_features, last_update;

    public Film(int film_id, String title, String description, int release_year, int rental_duration , int rental_rate, int length, int replacement_cost, String rating, String special_features, String last_update) {
        this.title = title;
        this.description = description;
        this.film_id = film_id;
        this.release_year = release_year;
        this.rental_duration = rental_duration;
        this.rental_rate = rental_rate;
        this.length = length;
        this.replacement_cost = replacement_cost;
        this.rating = rating;
        this.special_features = special_features;
        this.last_update = last_update;
    }

    public int getFilm_id() { return film_id; }
    public void setFilm_id(int film_id) {this.film_id = film_id;}

    public String getTitle(){ return title; }
    public void setTitle(String title) {this.title = title;}

    public String getDescription(){ return description; }
    public void setDescription(String description) {this.description = description;}

    public int getRelease_year() { return release_year;}
    public void setRelease_year(int release_year) {this.release_year = release_year;}

    public int getRental_duration() {return rental_duration;}
    public void setRental_duration(int rental_duration) {this.rental_duration = rental_duration;}

    public int getRental_rate() { return rental_rate;}
    public void setRental_rate(int rental_rate) {this.rental_rate = rental_rate;}

    public int getLength() {return length;}
    public void setLength(int length) {this.length = length;}

    public int getReplacement_cost() {return replacement_cost;}
    public void setReplacement_cost(int replacement_cost) {this.replacement_cost = replacement_cost;}

    public String getRating() {return rating;}
    public void setRating(String rating) {this.rating = rating;}

    public String getSpecial_features() {return special_features;}
    public void setSpecial_features(String special_features) {this.special_features = special_features;}

    public String getLast_update() {return last_update;}
    public void setLast_update(String last_update) {this.last_update = last_update;}

    @Override
    public String toString() {
        return " {}";
    }
}
