package com.example.harikakonagala.kubratest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Harika Konagala on 6/25/2017.
 */

public class UsersListAdapter extends BaseAdapter {

    List<User> users;
    Context context;
    private static LayoutInflater inflater;

    public UsersListAdapter(Context context, List<User> users) {
        this.users = users;
        this.context = context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(users.get(position).getId());
    }

    public class Holder{
        TextView username;
        TextView fullAddress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.users_list, null);
        holder.username = (TextView) rowView.findViewById(R.id.username);
        holder.fullAddress = (TextView) rowView.findViewById(R.id.fullAddress);
        holder.username.setText(users.get(position).getUsername());
        holder.fullAddress.setText(users.get(position).getAddress().toString());
        rowView.setTag(users.get(position).getId());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = String.valueOf(v.getTag());
                Log.d("USERID", String.valueOf(userId));
                Intent myIntent = new Intent(context, PostsActivity.class);
                myIntent.putExtra("userId", userId);
                context.startActivity(myIntent);
            }
        });
        return rowView;
    }
}
