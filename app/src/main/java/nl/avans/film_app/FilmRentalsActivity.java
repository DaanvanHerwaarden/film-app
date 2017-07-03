package nl.avans.film_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FilmRentalsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, RentalRequest.RentalListener {

    public static ArrayList<Film> rentals = new ArrayList<>();

    private Film film;
    private ListView listViewRentals;
    private RentalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_rentals);

        film = (Film) getIntent().getSerializableExtra("FILM");

        new RentalRequest(getApplicationContext(), this, film);

        listViewRentals = (ListView) findViewById(R.id.activity_film_rentals);
        listViewRentals.setOnItemClickListener(this);

        adapter = new RentalAdapter(getLayoutInflater(), rentals);
        listViewRentals.setAdapter(adapter);
    }

    @Override
    public void onRentalAvailable(ArrayList<Film> filmArrayList) {
        rentals.clear();
        rentals.addAll(filmArrayList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRentalAvailable(Film film) {
        rentals.add(film);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRentalError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Film film = rentals.get(position);
        Intent intent = new Intent(getApplicationContext(), FilmDetailActivity.class);
        intent.putExtra("FILM", film);
        startActivity(intent);
    }
}
