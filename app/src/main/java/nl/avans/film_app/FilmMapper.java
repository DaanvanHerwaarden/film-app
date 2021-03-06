package nl.avans.film_app;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class FilmMapper {

    // De JSON attributen die we uitlezen
    public static final String FILM_TITLE = "title";
    public static final String FILM_DESCRIPTION = "description";
    public static final String RATING = "rating";

    private static ArrayList<Film> films = new ArrayList<>();

    public static ArrayList<Film> mapFilmList(JSONObject jsonObject){
        try {
            Film film = new Film(
                    films.size(),
                    jsonObject.getString(FILM_TITLE),
                    jsonObject.getString(FILM_DESCRIPTION),
                    jsonObject.getString(RATING),
                    Film.FilmState.fromBit(0)
            );

            films.add(film);
        } catch(JSONException ex) {
            ex.printStackTrace();
            Log.e("FilmMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return films;
    }

    public static ArrayList<Film> getFilms() {
        return films;
    }
}