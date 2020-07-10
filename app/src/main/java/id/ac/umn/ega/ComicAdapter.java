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

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {

    private Context context;
    private ArrayList<ComicItem> ComicList;

    public ComicAdapter(Context context1, ArrayList<ComicItem> comicList) {
        context = context1;
        ComicList = comicList;
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_card_comics, parent, false);
        return new ComicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        ComicItem currentItem = ComicList.get(position);

        String ComicTitle = currentItem.getComicTitle();
        String ComicImage = currentItem.getComicImage();

        holder.ComicTitle.setText(ComicTitle);
        Picasso.with(context).load(ComicImage).fit().centerInside().into(holder.ComicImageView);
    }

    @Override
    public int getItemCount() {
        return ComicList.size();
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder {
        public ImageView ComicImageView;
        public TextView ComicTitle;

        public ComicViewHolder(View itemView) {
            super(itemView);
            ComicImageView = itemView.findViewById(R.id.image_view_comics);
            ComicTitle = itemView.findViewById(R.id.text_comics_name);
        }
    }
}
