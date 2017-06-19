package nl.avans.film_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.avans.film_app.Film;
import nl.avans.film_app.FilmMapper;

public class FilmRequest {

    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    // De aanroepende class implementeert deze interface.
    private FilmRequest.ToDoListener listener;

    /**
     * Constructor
     *
     * @param context
     * @param listener
     */
    public FilmRequest(Context context, FilmRequest.ToDoListener listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * Verstuur een GET request om alle ToDo's op te halen.
     */
    public void handleGetFilmRequests() {

        Log.i(TAG, "handleGetFilmRequests");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    Config.URL_FILMS,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response
                            Log.i(TAG, response.toString());
                            ArrayList<Film> result = FilmMapper.mapFilmList(response);
                            listener.onFilmAvailable(result);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // handleErrorResponse(error);
                            Log.e(TAG, error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
        }
    }

    /**
     * Verstuur een POST met nieuwe ToDo.
     */
    public void handlePostFilmRequest(final Film newFilm) {

        Log.i(TAG, "handlePostFilmRequest");

        // Haal het token uit de prefs
        // TODO Verplaats het ophalen van het token naar een centraal beschikbare 'utility funtion'
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            //
            // Maak een JSON object met username en password. Dit object sturen we mee
            // als request body (zoals je ook met Postman hebt gedaan)
            //
            String body = "{\"Titel\":\"" + newFilm.getTitle() + "\",\"Rating\":\"" + newFilm.getRating() + "\"}";

            try {
                JSONObject jsonBody = new JSONObject(body);
                Log.i(TAG, "handlePostFilm - body = " + jsonBody);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        Config.URL_FILMS,
                        jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i(TAG, response.toString());
                                // Het toevoegen is gelukt
                                // Hier kun je kiezen: of een refresh door de hele lijst op te halen
                                // en de ListView bij te werken ... Of alleen de ene update toevoegen
                                // aan de ArrayList. Wij doen dat laatste.
                                listener.onFilmsAvailable(newFilm);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error - send back to caller
                                listener.onFilmError(error.toString());
                            }
                        }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };

                // Access the RequestQueue through your singleton class.
                VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                listener.onFilmError(e.getMessage());
            }
        }
    }

    //
    // Callback interface - implemented by the calling class (MainActivity in our case).
    //
    public interface ToDoListener {
        // Callback function to return a fresh list of ToDos
        void onFilmAvailable(ArrayList<Film> toDos);

        // Callback function to handle a single added ToDo.
        void onFilmsAvailable(Film todo);

        // Callback to handle serverside API errors
        void onFilmError(String message);
    }

}
