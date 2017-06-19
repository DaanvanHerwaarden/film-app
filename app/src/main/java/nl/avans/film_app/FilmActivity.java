package nl.avans.film_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import static nl.avans.film_app.FilmMapper.FILM_RESULT;

/**
 * Created by Daan on 19-6-2017.
 */

public class FilmActivity extends AppCompatActivity {

    private TextView title;
    private TextView rating;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beschikbare_films);

        title = (TextView) findViewById(R.id.title);
        rating = (TextView) findViewById(R.id.rating);

    }
}
