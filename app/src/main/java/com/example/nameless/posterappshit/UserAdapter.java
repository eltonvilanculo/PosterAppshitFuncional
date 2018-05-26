package com.example.nameless.posterappshit;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nameless on 5/9/2018.
 */
class UserAdapter extends BaseAdapter {
    ArrayList<User> users = new ArrayList<>();
    private LayoutInflater inflater;

    public UserAdapter(LayoutInflater inflater) {
        Log.d("UserAdapter", "default constructor");
        this.inflater = inflater;

    }

    public void add(User user) {
        users.add(user);
       this.notifyDataSetChanged();
    }

    ArrayList<User> getList() {
        return users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.user_view, parent, false);
        TextView name = view.findViewById(R.id.user_name_user);
        TextView email = view.findViewById(R.id.user_email);

        name.setText(users.get(position).getUsername());
        email.setText(users.get(position).getEmail());

        return view;
    }

    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user)) {
                users.remove(i);
                users.add(i, user);
            }
        }
        this.notifyDataSetChanged();
    }
}
