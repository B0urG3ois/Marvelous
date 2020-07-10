package id.ac.umn.ega;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {

    private Context context;
    private ArrayList<SeriesItem> seriesList;

    public SeriesAdapter(Context context1, ArrayList<SeriesItem> SeriesList) {
        context = context1;
        seriesList = SeriesList;
    }

    @Override
    public SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_card_series, parent, false);
        return new SeriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SeriesViewHolder holder, int position) {
        SeriesItem currentItem = seriesList.get(position);

        String SeriesTitle = currentItem.getSeriesTitle();
        String SeriesImg = currentItem.getSeriesImage();

        holder.SeriesTitle.setText(SeriesTitle);
        Picasso.with(context).load(SeriesImg).fit().centerInside().into(holder.SeriesImageView);
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public class SeriesViewHolder extends RecyclerView.ViewHolder {
        public ImageView SeriesImageView;
        public TextView SeriesTitle;


        public SeriesViewHolder(View itemView) {
            super(itemView);
            SeriesImageView = itemView.findViewById(R.id.image_view_series);
            SeriesTitle = itemView.findViewById(R.id.text_series_name);
        }
    }

}
