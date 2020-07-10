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

public class ComicsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewComic;
    private ComicAdapter comicAdapter;
    private ArrayList<ComicItem> comicList;
    private RequestQueue requestQueue;

    String CharID, CharName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);

        Intent intent = getIntent();
        CharID = intent.getStringExtra("CharID");
        CharName = intent.getStringExtra("CharName");

        setTitle(CharName + "'s Comics");

        recyclerViewComic = findViewById(R.id.recycler_view_comics);
        recyclerViewComic.setHasFixedSize(true);
        recyclerViewComic.setLayoutManager(new LinearLayoutManager(this));

        comicList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "http://gateway.marvel.com/v1/public/characters/" +  CharID + "/comics?ts=1&apikey=261b976ebae3221cba47093ac5c06150&hash=d25821ae0c83becfa1bacc567bde3f63";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("results");

                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject comic = jsonArray.getJSONObject(i);

                                String ComicTitle = comic.getString("title");

                                JSONObject comicImg = comic.getJSONObject("thumbnail");
                                String comicImage = comicImg.getString("path");
                                String comicType = comicImg.getString("extension");
                                String comicPath = comicImage + "/portrait_xlarge." + comicType;

                                comicList.add(new ComicItem(ComicTitle, comicPath));
                            }
                            comicAdapter = new ComicAdapter(ComicsActivity.this, comicList);
                            recyclerViewComic.setAdapter(comicAdapter);
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
