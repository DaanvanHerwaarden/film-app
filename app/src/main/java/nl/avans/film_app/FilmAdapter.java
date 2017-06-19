package nl.avans.film_app;

/**
 * Created by Daan on 19-6-2017.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmAdapter extends ArrayAdapter<Film> {

    public static final String TAG = FilmAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Film> items;

    private static class ViewHolder {
        TextView title, description;


    }

    public FilmAdapter(Context context, ArrayList<Film> items){
        super(context, R.layout.beschikbare_films, items);
        this.mContext = context;
        this.items = items;
        Log.d(TAG, "Consturctor called");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView Called");
        Film item = items.get(position);

        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.beschikbare_films, parent, false);

            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description) ;


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(item.getTitle());
        viewHolder.description.setText(item.getDescription());

        return convertView;
    }
}
