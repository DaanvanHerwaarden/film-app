package nl.avans.film_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilmRequest {

    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    // De aanroepende class implementeert deze interface
    private FilmListener listener;

    public FilmRequest(Context context, FilmListener listener) {
        this.context = context;
        this.listener = listener;
    }

    //Verstuur een GET request om alle films op te halen
    public void handleGetFilmRequests() {
        Log.i(TAG, "handleGetFilmRequests");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonArrayRequest jsObjRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    Config.URL_FILMS + "?count=100&offset=0",
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                Log.i("Request", "noo");
                                for (int i = 0; i < 100; i ++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    FilmMapper.mapFilmList(jsonObject);
                                }
                                listener.onFilmAvailable(FilmMapper.getFilms());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
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

    // Callback interface - implemented by the calling class (MainActivity in our case).
    public interface FilmListener {
        // Callback function to return a fresh list of ToDos
        void onFilmAvailable(ArrayList<Film> toDos);

        // Callback function to handle a single added ToDo.
        void onFilmsAvailable(Film todo);

        // Callback to handle serverside API errors
        void onFilmError(String message);
    }
}