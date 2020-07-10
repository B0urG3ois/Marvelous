package id.ac.umn.ega;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;

import static id.ac.umn.ega.MainActivity.EXTRA_DESC;
import static id.ac.umn.ega.MainActivity.EXTRA_ID;
import static id.ac.umn.ega.MainActivity.EXTRA_IMAGE;
import static id.ac.umn.ega.MainActivity.EXTRA_NAME;

public class DetailsBasicActivity extends AppCompatActivity {

    NotifHelper notifHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notifHelper = new NotifHelper(this);

        Intent intent = getIntent();

        final String CharID = intent.getStringExtra(EXTRA_ID);
        String imagePath = intent.getStringExtra(EXTRA_IMAGE);
        final String CharName = intent.getStringExtra(EXTRA_NAME);
        String CharDesc = intent.getStringExtra(EXTRA_DESC);

        setTitle(CharName + "'s Details");


        ImageView imageView = findViewById(R.id.image_view_details);
        TextView txtViewName = findViewById(R.id.text_view_name);
        TextView txtViewDesc = findViewById(R.id.text_view_desc);
        ImageButton btnComics = findViewById(R.id.button_comics_img);
        ImageButton btnSeries = findViewById(R.id.button_series_img);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/badaboom.TTF");
        txtViewName.setTypeface(typeface);
        txtViewDesc.setTypeface(typeface);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, CharName + " has been registered!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Notification.Builder builder = notifHelper.getDataNotif("Data registered from " + CharName + "'s Details", CharName);
                        notifHelper.notifManager().notify(new Random().nextInt(), builder.build());
                    }
                }, 5000);
            }
        });

        btnComics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailsBasicActivity.this, ComicsActivity.class);

                intent1.putExtra("CharID", CharID);
                intent1.putExtra("CharName", CharName);
                startActivity(intent1);
            }
        });

        btnSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(DetailsBasicActivity.this, SeriesActivity.class);

                intent2.putExtra("CharID", CharID);
                intent2.putExtra("CharName", CharName);
                startActivity(intent2);
            }
        });

        Picasso.with(this).load(imagePath).fit().centerInside().into(imageView);
        txtViewName.setText(CharName);
        txtViewDesc.setText(CharDesc);
    }
}
