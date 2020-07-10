package id.ac.umn.ega;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

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

public class MainActivity extends AppCompatActivity implements DataAdapter.OnItemClickListener {

    public static final String EXTRA_ID = "CharactersID";
    public static final String EXTRA_IMAGE = "CharactersImage";
    public static final String EXTRA_NAME = "CharactersName";
    public static final String EXTRA_DESC = "CharactersDesc";

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private ArrayList<DataItem> dataList;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    SessionManagement sessionManagement;

    EditText searching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManagement = new SessionManagement(getApplicationContext());
        sessionManagement.checkLogin();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();

        searching = findViewById(R.id.searching);
        searching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final ArrayList<DataItem> filteredNames = new ArrayList<>();

                for (DataItem items : dataList) {
                    if (items.getCharactersName().toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredNames.add(items);
                    }
                }
                dataAdapter.filterList(filteredNames);
            }
        });
    }

    private void parseJSON() {
        String url = "http://gateway.marvel.com/v1/public/characters?ts=1&limit=100&apikey=261b976ebae3221cba47093ac5c06150&hash=d25821ae0c83becfa1bacc567bde3f63";

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Marvel Characters....");
        progressDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObjectData = response.getJSONObject("data");
                    JSONArray jsonArray = jsonObjectData.getJSONArray("results");

                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);

                        String CharactersID = data.getString("id");
                        String CharactersName = data.getString("name");
                        String CharactersDescription = data.getString("description");

                        JSONObject imgData = data.getJSONObject("thumbnail");
                        String imageData = imgData.getString("path");
                        String imageType = imgData.getString("extension");
                        String imagePath = imageData + "/portrait_xlarge." + imageType;

                        dataList.add(new DataItem(CharactersID, CharactersName, imagePath, CharactersDescription));
                    }

                    dataAdapter = new DataAdapter(MainActivity.this, dataList);
                    recyclerView.setAdapter(dataAdapter);

                    dataAdapter.setOnItemClickListener(MainActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
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

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, DetailsBasicActivity.class);
        DataItem clickedItem = dataList.get(position);

        intent.putExtra(EXTRA_ID, clickedItem.getCharactersID());
        intent.putExtra(EXTRA_IMAGE, clickedItem.getCharactersImage());
        intent.putExtra(EXTRA_NAME, clickedItem.getCharactersName());
        intent.putExtra(EXTRA_DESC, clickedItem.getCharactersDescription());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                sessionManagement.logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
