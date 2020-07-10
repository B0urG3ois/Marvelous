package id.ac.umn.ega;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView nama = findViewById(R.id.nama);
        TextView referensi = findViewById(R.id.referensi);
        TextView no1 = findViewById(R.id.no1);
        TextView no2 = findViewById(R.id.no2);
        TextView no3 = findViewById(R.id.no3);
        TextView no4 = findViewById(R.id.no4);
        TextView no5 = findViewById(R.id.no5);
        TextView no6 = findViewById(R.id.no6);
        TextView no7 = findViewById(R.id.no7);
        TextView no8 = findViewById(R.id.no8);
        TextView no9 = findViewById(R.id.no9);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/badaboom.TTF");
        nama.setTypeface(typeface);
        referensi.setTypeface(typeface);
        no1.setTypeface(typeface);
        no2.setTypeface(typeface);
        no3.setTypeface(typeface);
        no4.setTypeface(typeface);
        no5.setTypeface(typeface);
        no6.setTypeface(typeface);
        no7.setTypeface(typeface);
        no8.setTypeface(typeface);
        no9.setTypeface(typeface);
    }
}
