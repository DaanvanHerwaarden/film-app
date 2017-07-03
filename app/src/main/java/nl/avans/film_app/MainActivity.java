package nl.avans.film_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText email, firstName, lastName;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");

        this.button = (Button) findViewById(R.id.activity_main_button);
        this.email = (EditText) findViewById(R.id.activity_main_email);
        this.register = (TextView) findViewById(R.id.activity_main_register);
        this.firstName = (EditText) findViewById(R.id.activity_main_first_name);
        this.lastName = (EditText) findViewById(R.id.activity_main_last_name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //De database ondersteunt geen wachtwoorden, dus we pakken alleen de voornaam, achternaam en email
                String body = "{ \"firstName\": \"" + firstName.getText().toString() + "\", \"lastName\": \"" + lastName.getText().toString() + "\", \"email\": \"" + email.getText().toString() + "\" }";
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
<<<<<<< HEAD
                                    // Succesvol response - dat betekent dat we een geldig token hebben.
=======
                                    // We hebben nu het token. We kiezen er hier voor om
                                    // het token in SharedPreferences op te slaan. Op die manier
                                    // is het token tussen app-stop en -herstart beschikbaar -
                                    // totdat het token expired.
>>>>>>> 563a5017b7a58461ed6a8ef5d8a212c52e81a95a
                                    try {
                                        String token = response.getString("token");

                                        Context context = getApplicationContext();
                                        SharedPreferences sharedPref = context.getSharedPreferences(
                                                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString(getString(R.string.saved_token), token);
                                        editor.commit();

                                        Log.i("Login", "Token = " + token);

                                        Intent intent = new Intent(getApplicationContext(), FilmActivity.class);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
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
}