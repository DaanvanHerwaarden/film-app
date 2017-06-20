package nl.avans.film_app;

/**
 * Created by Daan on 19-6-2017.
 */
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmAdapter extends BaseAdapter {

    private ArrayList<Film> films;
    private LayoutInflater inflater;

    public FilmAdapter(LayoutInflater inflater, ArrayList<Film> films) {
        this.inflater = inflater;
        this.films = films;
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int position) {
        return films.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Film film = (Film) getItem(position);

        if (convertView == null ) {
            convertView = inflater.inflate(R.layout.film_detail, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);

        title.setText(film.getTitle());
        description.setText(film.getDescription());
        rating.setText(film.getRating());

        return convertView;
    }
}
