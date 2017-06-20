package nl.avans.film_app;

/**
 * Created by Daan on 18-6-2017.
 */

import java.io.Serializable;

public class Film implements Serializable {

    private FilmState state;
    private int film_id, original_language_id, release_year, language_id, rental_duration, rental_rate, length, replacement_cost;
    private String title, description, rating, special_features, last_update;

    public Film(int id, String title, String description, String rating, FilmState state) {
        this.film_id = id;
        this.description = description;
        this.rating = rating;
        this.state = state;
        this.title = title;
    }

    public Film(int film_id, String title, String description, int release_year, int rental_duration , int rental_rate, int length, int replacement_cost, String rating, String special_features, String last_update) {
        this.title = title;
        this.description = description;
        this.film_id = film_id;
        this.release_year = release_year;
        this.language_id = language_id;
        this.original_language_id = original_language_id;
        this.rental_duration = rental_duration;
        this.rental_rate = rental_rate;
        this.length = length;
        this.replacement_cost = replacement_cost;
        this.rating = rating;
        this.special_features = special_features;
        this.last_update = last_update;
    }
      
    public enum FilmState {

        AVAILABLE("Verkrijgbaar", "Huur deze film", 0), RESERVED("Gereserveed", "Niet beschikbaar", 1);

        private int bit;
        private String buttonText, string;

        private FilmState(String string, String buttonText, int bit) {
            this.string = string;
            this.bit = bit;
            this.buttonText = buttonText;
        }

        public static FilmState fromBit(int bit) {
            for (FilmState state : values()) {
                if (state.getBit() == bit) {
                    return state;
                }
            }
            return null;
        }

        public int getBit() {
            return bit;
        }

        public String getButtonText() {
            return buttonText;
        }

        public String getString() {
            return string;
        }
    }

    public int getFilm_id() { return film_id; }
    public void setFilm_id(int film_id) {this.film_id = film_id;}

    public String getTitle(){ return title; }
    public void setTitle(String title) {this.title = title;}

    public String getDescription(){ return description; }
    public void setDescription(String description) {this.description = description;}

    public int getOriginal_language_id() { return original_language_id; }
    public void setOriginal_language_id(int original_language_id) {this.original_language_id = original_language_id;}

    public int getLanguage_id() {return language_id;}
    public void setLanguage_id(int language_id) {this.language_id = language_id;}

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

    public FilmState getState() {
        return state;
    }

    public void setState(FilmState state) {
        this.state = state;
    }

    public String getLast_update() {return last_update;}
    public void setLast_update(String last_update) {this.last_update = last_update;}

    @Override
    public String toString() {
        return " {}";
    }
}
