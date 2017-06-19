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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button button;
    private EditText email, password, username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.button = (Button) findViewById(R.id.activity_register_button);
        this.email = (EditText) findViewById(R.id.activity_register_email);
        this.password = (EditText) findViewById(R.id.activity_register_password);
        this.username = (EditText) findViewById(R.id.activity_register_username);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = "{ \"first_name\": \"" + username.getText().toString() + "\", \"last_name\": \"" + password.getText().toString() + "\", \"email\": \"" + email.getText().toString() + "\" }";
                Log.i("Login", body);

                try {
                    JSONObject jsonBody = new JSONObject(body);
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            Config.SERVER_ADDRESS + "/api/v1/register",
                            jsonBody,
                            new com.android.volley.Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    finish();
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

                    VolleyRequestQueue.getInstance(RegisterActivity.this).addToRequestQueue(jsObjRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}