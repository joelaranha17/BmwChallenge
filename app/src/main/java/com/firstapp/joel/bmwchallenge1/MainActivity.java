package com.firstapp.joel.bmwchallenge1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Declarations
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    List<Get_SetData> mylist=new ArrayList<>();
    String mydata;

    public static String myurl = "http://localsearch.azurewebsites.net/api/Locations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set RecyclerView and Adapter
        recyclerView = (RecyclerView)findViewById(R.id.myrecycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        new JSONPARSER().execute();

    }

    //Menu on ToolBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortname:
                return true;

            case R.id.sortdistance:
                return true;

            case R.id.sortarrivaltime:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class JSONPARSER extends AsyncTask<String,String,String>{
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            pdLoading.dismiss();

            try {
                JSONArray jArray = new JSONArray(mydata);
                for(int i=0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    Get_SetData data = new Get_SetData();
                    data.setAddress(jObject.getString("Address"));
                    data.setId(jObject.getInt("ID"));
                    data.setLatitude(jObject.getDouble("Latitude"));
                    data.setLongitude(jObject.getDouble("Longitude"));
                    data.setName(jObject.getString("Name"));
                    data.setArrival_time(jObject.getString("ArrivalTime"));
                    mylist.add(data);

                }

                mAdapter = new MyAdapter(MainActivity.this,mylist);
                recyclerView.setAdapter(mAdapter);

            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }

            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(myurl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);

                }

                mydata =  buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
