package nl.avans.film_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FilmActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, FilmRequest.FilmListener {

    private ArrayList<Film> films;
    private Button logOut, myFilms;
    private ListView listViewFilms;
    private BaseAdapter filmAdapter;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beschikbare_films);

        this.logOut = (Button) findViewById(R.id.activity_film_list_logout);
        this.myFilms = (Button) findViewById(R.id.activity_film_list_myfilms);

        films = new ArrayList<>();

        new FilmRequest(getApplicationContext(), this);

        //link adapter to listview
        listViewFilms = (ListView) findViewById(R.id.lijstfilms);
        listViewFilms.setOnItemClickListener(this);

        //
        filmAdapter = new FilmAdapter(getLayoutInflater(), films);
        listViewFilms.setAdapter(filmAdapter);

        getFilm();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("saved_token");
                editor.commit();

                FilmRentalsActivity.rentals.clear();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        myFilms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FilmRentalsActivity.class));
            }
        });
    }

    private void getFilm(){
        FilmRequest request = new FilmRequest(getApplicationContext(), this);
        request.handleGetFilmRequests();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "Position " + position + " is geselecteerd");
        Intent intent = new Intent(getApplicationContext(), FilmDetailActivity.class);
        intent.putExtra("FILM", films.get(position));
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
}
