package com.example.harikakonagala.kubratest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PostsActivity extends AppCompatActivity {

    ListView postsList;
    String userId;
    Button sortButton;
    boolean sortAsc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        Intent myIntent = getIntent();
        userId = myIntent.getStringExtra("userId");
        postsList = (ListView) findViewById(R.id.postsList);
        sortButton = (Button) findViewById(R.id.sortButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] input = new String[1];
        input[0] = "X"; // default
        GetPostsTask getPostsTask = new GetPostsTask();
        getPostsTask.execute(input);
        sortButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String[] input = new String[1];
                if (sortAsc) {
                    input[0] = "A"; // ASC
                } else{
                    input[0] = "D"; // DESC
                }
                sortAsc = !(sortAsc);
                GetPostsTask getPostsTask = new GetPostsTask();
                getPostsTask.execute(input);
            }
        });
    }

    private class GetPostsTask extends AsyncTask<String, Integer, List<Post>> {

        @Override
        protected List<Post> doInBackground(String... params) {
            URL url;
            String response = "";
            String sortBy = params[0];
            String requestUrl = "http://jsonplaceholder.typicode.com/posts?userId=" + userId;
            List<Post> posts = new ArrayList<>();
            String inputLine;
            try{
                url = new URL(requestUrl);
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                myConnection.setReadTimeout(15000);
                myConnection.setConnectTimeout(15000);
                myConnection.setRequestMethod("GET");
                myConnection.connect();
                InputStreamReader streamReader = new InputStreamReader(myConnection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                response = stringBuilder.toString();
                Log.d("RESPONSE BODY: ", response);

                JSONArray jsonArray = new JSONArray(response);
                if(jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject childJsonObj = jsonArray.getJSONObject(i);
                        Post post = new Post(childJsonObj.getString("userId"),
                                childJsonObj.getString("id"),
                                childJsonObj.getString("title"),
                                childJsonObj.getString("body"));
                        posts.add(post);
                    }
                }
                myConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            if (sortBy.equalsIgnoreCase("A")){
                Collections.sort(posts, new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                });
            } else if (sortBy.equalsIgnoreCase("D")){
                Collections.sort(posts, new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        return -1*(o1.getTitle().compareTo(o2.getTitle()));
                    }
                });
            }
            return posts;
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            super.onPostExecute(posts);
            postsList.setAdapter(new PostsListAdapter(getApplicationContext(), posts));
        }
    }
}
