package nl.avans.film_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RentalRequest {

    private Context context;
    private Film film;
    public final String TAG = this.getClass().getSimpleName();

    // De aanroepende class implementeert deze interface
    private RentalListener listener;

    public RentalRequest(Context context, RentalListener listener, Film film) {
        this.context = context;
        this.film = film;
        this.listener = listener;
    }

    //Verstuur een GET request om alle films op te halen
    public void handleGetRentalRequests() {
        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");

        if (token != null && !token.equals("dummy default token")) {
            JsonArrayRequest jsObjRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    Config.URL_RENTALS + film.getFilm_id(),
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < 50; i ++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    RentalMapper.mapFilmList(jsonObject);
                                }
                                listener.onRentalAvailable(RentalMapper.getRentals());
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
    public interface RentalListener {
        // Callback function to return a fresh list of rental
        void onRentalAvailable(ArrayList<Film> films);

        // Callback function to handle a single added rental.
        void onRentalAvailable(Film todo);

        // Callback to handle serverside API errors
        void onRentalError(String message);
    }
}