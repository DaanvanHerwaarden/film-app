package nl.avans.film_app;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class FilmMapper {

    // De JSON attributen die we uitlezen
    public static final String FILM_ID = "film_id";
    public static final String FILM_RESULT = "result";
    public static final String FILM_TITLE = "title";
    public static final String FILM_DESCRIPTION = "description";
    public static final String RELEASE_YEAR = "release_year";
    public static final String RENTAL_DURATION = "rental_duration";
    public static final String RENTAL_RATE = "rental_rate";
    public static final String LENGTH = "length";
    public static final String REPLACEMENT_COST = "replacement_cost";
    public static final String RATING = "rating";
    public static final String SPECIAL_FEATURES = "special_features";
    public static final String LAST_UPDATE = "last_update";



    /**
     * Map het JSON response op een arraylist en retourneer deze.
     */
    public static ArrayList<Film> mapFilmList(JSONObject response){

        ArrayList<Film> result = new ArrayList<>();

        try{
            JSONArray jsonArray = response.getJSONArray(FILM_RESULT);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Convert stringdate to Date
                //String timestamp = jsonObject.getString(TODO_UPDATED_AT);
                //DateTime todoDateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(timestamp);

                Film film = new Film(
                        jsonObject.getInt(FILM_ID),
                        jsonObject.getString(FILM_TITLE),
                        jsonObject.getString(FILM_DESCRIPTION),
                        jsonObject.getInt(RELEASE_YEAR),
                        jsonObject.getInt(RENTAL_DURATION),
                        jsonObject.getInt(RENTAL_RATE),
                        jsonObject.getInt(LENGTH),
                        jsonObject.getInt(REPLACEMENT_COST),
                        jsonObject.getString(RATING),
                        jsonObject.getString(SPECIAL_FEATURES),
                        jsonObject.getString(LAST_UPDATE)

                );
                // Log.i("ToDoMapper", "ToDo: " + toDo);
                result.add(film);
                Log.d("film", film.getTitle());
            }
        } catch( JSONException ex) {
            Log.e("FilmMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
