package nl.avans.film_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static nl.avans.film_app.FilmMapper.FILM_RESULT;
import static nl.avans.film_app.MainActivity.MY_REQUEST_CODE;

public class FilmActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, FilmRequest.FilmListener {

    private ArrayList<Film> films;
    private ListView listViewFilms;
    private BaseAdapter filmAdapter;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beschikbare_films);

        films = new ArrayList<>();
        films.add(new Film("Titel", "Hey", "Test"));

        listViewFilms = (ListView) findViewById(R.id.lijstfilms);
        listViewFilms.setOnItemClickListener(this);

        filmAdapter = new FilmAdapter(getLayoutInflater(), films);
        listViewFilms.setAdapter(filmAdapter);

        getFilm();
    }

    public void onFilmAvailable(Film todo) {
        films.add(todo);
        filmAdapter.notifyDataSetChanged();
    }

    private void getFilm(){
        FilmRequest request = new FilmRequest(getApplicationContext(), this);
        request.handleGetFilmRequests();
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

    private void postFilm(Film film){
        FilmRequest request = new FilmRequest(getApplicationContext(), this);
        request.handlePostFilmRequest(film);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent pData) {
        if ( requestCode == MY_REQUEST_CODE ) {
            Log.v( TAG, "onActivityResult OK" );
            if (resultCode == Activity.RESULT_OK) {
                final Film newToDo = (Film) pData.getSerializableExtra(FILM_RESULT);
                Log.v(TAG, "Retrieved Value newToDo is " + newToDo);

                //Sla de film op
                postFilm(newToDo);
            }
        }
    }
}
