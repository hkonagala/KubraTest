package com.example.harikakonagala.kubratest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Harika Konagala on 6/25/2017.
 */

public class PostsListAdapter extends BaseAdapter{
    List<Post> posts;
    Context context;
    private static LayoutInflater inflater;

    public PostsListAdapter(Context context, List<Post> posts) {
        this.posts = posts;
        this.context = context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        TextView title;
        TextView body;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.posts_list, null);
        holder.title = (TextView) rowView.findViewById(R.id.title);
        holder.body = (TextView) rowView.findViewById(R.id.body);
        holder.title.setText(posts.get(position).getTitle());
        holder.body.setText(posts.get(position).getBody().toString());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rowView;
    }
}
