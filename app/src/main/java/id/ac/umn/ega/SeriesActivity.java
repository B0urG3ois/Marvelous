package id.ac.umn.ega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeriesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSeries;
    private SeriesAdapter seriesAdapter;
    private ArrayList<SeriesItem> seriesList;
    private RequestQueue requestQueue;

    String CharID, CharName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        Intent intent = getIntent();
        CharID = intent.getStringExtra("CharID");
        CharName = intent.getStringExtra("CharName");

        setTitle(CharName + "'s Series");

        recyclerViewSeries = findViewById(R.id.recycler_view_series);
        recyclerViewSeries.setHasFixedSize(true);
        recyclerViewSeries.setLayoutManager(new LinearLayoutManager(this));

        seriesList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {


        String url = "http://gateway.marvel.com/v1/public/characters/" + CharID + "/series?ts=1&apikey=261b976ebae3221cba47093ac5c06150&hash=d25821ae0c83becfa1bacc567bde3f63";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("results");

                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject series = jsonArray.getJSONObject(i);

                                String SeriesTitle = series.getString("title");

                                JSONObject seriesImg = series.getJSONObject("thumbnail");
                                String seriesImage = seriesImg.getString("path");
                                String seriesType = seriesImg.getString("extension");
                                String seriesPath = seriesImage + "/portrait_xlarge." + seriesType;

                                seriesList.add(new SeriesItem(SeriesTitle, seriesPath));
                            }
                            seriesAdapter = new SeriesAdapter(SeriesActivity.this, seriesList);
                            recyclerViewSeries.setAdapter(seriesAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 2500;
            }

            @Override
            public int getCurrentRetryCount() {
                return 2500;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        requestQueue.add(request);
    }
}
