package nl.avans.film_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import nl.avans.film_app.Film.FilmState;

public class FilmDetailActivity extends AppCompatActivity {

    private Button button;
    private Film film;
    private TextView description, rating, status, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        this.film = (Film) getIntent().getSerializableExtra("FILM");

        this.button = (Button) findViewById(R.id.activity_film_detail_button);
        this.description = (TextView) findViewById(R.id.activity_film_detail_description);
        this.rating = (TextView) findViewById(R.id.activity_film_detail_rating);
        this.status = (TextView) findViewById(R.id.activity_film_detail_status);
        this.title = (TextView) findViewById(R.id.activity_film_detail_title);

        description.setText(film.getDescription());
        rating.setText(film.getRating());
        status.setText(film.getState().toString());
        title.setText(film.getTitle());

        button.setClickable(film.getState() == FilmState.AVAILABLE);
        button.setText(film.getState().getButtonText());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Noteer de film als gehuurd
            }
        });
    }
}