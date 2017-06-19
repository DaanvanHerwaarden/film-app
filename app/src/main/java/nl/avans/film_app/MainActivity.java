package nl.avans.film_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, FilmRequest.ToDoListener, View.OnClickListener {

    private Button button;
    private EditText password, username;
    private TextView register;

    public final String TAG = this.getClass().getSimpleName();
    public final static String FILM_RESULT = "FILMRESULT";
    public static final int MY_REQUEST_CODE = 1234;

    private ListView listViewFilms;
    private BaseAdapter filmAdapter;
    private ArrayList<Film> films = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.button = (Button) findViewById(R.id.activity_login_button);
        this.password = (EditText) findViewById(R.id.activity_login_password);
        this.register = (TextView) findViewById(R.id.activity_login_register);
        this.username = (EditText) findViewById(R.id.activity_login_username);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = "{ \"username\": \"" + username.getText().toString() + "\", \"password\": \"" + password.getText().toString() + "\" }";
                Log.i("Login", body);

                try {

                    JSONObject jsonBody = new JSONObject(body);
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            Config.SERVER_ADDRESS + "/api/v1/login",
                            jsonBody,
                            new com.android.volley.Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Succesvol response - dat betekent dat we een geldig token hebben.

                                    // We hebben nu het token. We kiezen er hier voor om
                                    // het token in SharedPreferences op te slaan. Op die manier
                                    // is het token tussen app-stop en -herstart beschikbaar -
                                    // totdat het token expired.
                                    try {
                                        String token = response.getString("token");

                                        Context context = getApplicationContext();
                                        SharedPreferences sharedPref = context.getSharedPreferences(
                                                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString(getString(R.string.saved_token), token);
                                        editor.commit();

                                        listViewFilms = (ListView) findViewById(R.id.lijstfilms);
                                        listViewFilms.setOnItemClickListener(this);

                                        filmAdapter = new FilmAdapter(this, films);
                                        listViewFilms.setAdapter(filmAdapter);

                                        getFilm();

                                        Intent intent = new Intent(getApplicationContext(), FilmActivity.class);
                                        startActivity(intent);

                                        Log.i("Login", "Token = " + token);
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    error.printStackTrace();
                                    Log.i("test2","test3");
                                }
                            }) {
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }
                    };

                    jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(1500, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    VolleyRequestQueue.getInstance(MainActivity.this).addToRequestQueue(jsObjRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent pData)
    {
        if ( requestCode == MY_REQUEST_CODE )
        {
            Log.v( TAG, "onActivityResult OK" );
            if (resultCode == Activity.RESULT_OK )
            {
                final Film newToDo = (Film) pData.getSerializableExtra(FILM_RESULT);
                Log.v( TAG, "Retrieved Value newToDo is " + newToDo);

                // We need to save our new ToDo
                postFilm(newToDo);
            }
        }
    }

    public void onFilmAvailable(Film todo) {
        films.add(todo);
        filmAdapter.notifyDataSetChanged();
    }

    /**
     * Start the activity to GET all ToDos from the server.
     */
    private void getFilm(){
        FilmRequest request = new FilmRequest(getApplicationContext(), this);
        request.handleGetFilmRequests();
    }

    /**
     * Start the activity to POST a new ToDo to the server.
     */
    private void postFilm(Film film){
        FilmRequest request = new FilmRequest(getApplicationContext(), this);
        request.handlePostFilmRequest(film);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "Position " + position + " is geselecteerd");

        Film film = films.get(position);
        Intent intent = new Intent(getApplicationContext(), FilmActivity.class);
        intent.putExtra(FILM_RESULT, film);
        startActivity(intent);
    }

    @Override
    public void onFilmAvailable(ArrayList<Film> filmArrayList) {
        Log.i(TAG, "We hebben " + filmArrayList.size() + " items in de lijst");

        films.clear();
        for(int i = 0; i < filmArrayList.size(); i++) {
            films.add(filmArrayList.get(i));
        }
        filmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFilmsAvailable(Film film) {
        films.add(film);
        filmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFilmError(String message) {
        Log.e(TAG, message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

    }
}