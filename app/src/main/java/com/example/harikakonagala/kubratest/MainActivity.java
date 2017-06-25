package com.example.harikakonagala.kubratest;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.usersList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetUsersTask getUsersTask = new GetUsersTask();
        getUsersTask.execute();
    }

    private class GetUsersTask extends AsyncTask<Void, Integer, List<User>>{

        @Override
        protected List<User> doInBackground(Void... params) {
            URL url;
            String response = "";
            String requestJsonString = "{}";
            String requestUrl = "http://jsonplaceholder.typicode.com/users";
            List<User> users = new ArrayList<>();
            String inputLine;
            try{
                url = new URL(requestUrl);
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                myConnection.setReadTimeout(15000);
                myConnection.setConnectTimeout(15000);
                myConnection.setRequestMethod("GET");

                //Connect to our url
                myConnection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(myConnection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                response = stringBuilder.toString();
                Log.d("RESPONSE BODY: ", response);

                JSONArray jsonArray = new JSONArray(response);
                if(jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject childJsonObj = jsonArray.getJSONObject(i);
                        JSONObject addressJson = childJsonObj.getJSONObject("address");
                        JSONObject locationJson = addressJson.getJSONObject("geo");
                        Address address = new Address(addressJson.getString("street"),
                                addressJson.getString("suite"),
                                addressJson.getString("city"),
                                addressJson.getString("zipcode"),
                                locationJson.getString("lat"),
                                locationJson.getString("lng")
                                );
                            User user = new User(childJsonObj.getString("id"),
                                    childJsonObj.getString("name"),
                                    childJsonObj.getString("username"),
                                    address);
                            users.add(user);
                    }
                }
                myConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            listView.setAdapter(new UsersListAdapter(getApplicationContext(), users));
        }
    }

}
