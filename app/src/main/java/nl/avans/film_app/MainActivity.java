package nl.avans.film_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText password, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.button = (Button) findViewById(R.id.activity_login_button);
        this.password = (EditText) findViewById(R.id.activity_login_password);
        this.username = (EditText) findViewById(R.id.activity_login_username);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Login", username.getText().toString() + " " + password.getText().toString());
            }
        });
    }
}