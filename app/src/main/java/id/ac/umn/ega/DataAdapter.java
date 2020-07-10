package id.ac.umn.ega;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private Context context;
    private ArrayList<DataItem> DataList, names;
    private OnItemClickListener listener;

    NotifHelper notifHelper;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener1) {
        listener = listener1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DataAdapter(Context context1, ArrayList<DataItem> dataList) {
        context = context1;
        DataList = dataList;
        names = dataList;
        notifHelper = new NotifHelper(context);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        DataItem currentItem = DataList.get(position);

        String CharactersID = currentItem.getCharactersID();
        final String CharactersName = currentItem.getCharactersName();
        String CharactersImage = currentItem.getCharactersImage();
        String CharactersDescription = currentItem.getCharactersDescription();

        holder.CharactersTextView.setText(CharactersName);
        Picasso.with(context).load(CharactersImage).fit().centerInside().into(holder.CharactersImageView);

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, CharactersName + " has been registered!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void run() {
                            Notification.Builder builder = notifHelper.getDataNotif("Data registered from Home", CharactersName);
                            notifHelper.notifManager().notify(new Random().nextInt(), builder.build());
                        }
                    }, 5000);
                } else {
                    Toast.makeText(context, CharactersName + " - Short Click", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ImageView CharactersImageView;
        public TextView CharactersTextView;
        private ItemClickListener clickListener;

        public DataViewHolder(View itemView) {
            super(itemView);
            CharactersImageView = itemView.findViewById(R.id.image_view);
            CharactersTextView = itemView.findViewById(R.id.textDataAPI);

            itemView.setTag(itemView);
            /*itemView.setOnClickListener(this);*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = (ItemClickListener) itemClickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onClick(v, getPosition(), true);
            return true;
        }
    }

    public void filterList(ArrayList<DataItem> filteredTitles) {
        this.DataList = filteredTitles;
        notifyDataSetChanged();
    }

}
