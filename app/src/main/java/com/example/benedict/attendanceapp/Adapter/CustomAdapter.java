package com.example.benedict.attendanceapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benedict.attendanceapp.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Activity activity;
    List<StudentList> users;
    LayoutInflater inflater;

    //short to create constructer using command+n for mac & Alt+Insert for window

    public CustomAdapter(Activity activity, List<StudentList> users) {
        this.activity = activity;
        this.users = users;

        inflater = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null) {

            view = inflater.inflate(R.layout.list_view_item, viewGroup, false);

            holder = new ViewHolder();

            holder.tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            holder.ivCheckBox = (ImageView) view.findViewById(R.id.iv_check_box);

            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        StudentList model = users.get(i);

        holder.tvUserName.setText(model.getstudentName());

        if (model.isSelected())
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked);

        else
            holder.ivCheckBox.setBackgroundResource(R.drawable.check);

        return view;

    }

    public void updateRecords(List<StudentList> users) {
        this.users = users;

        notifyDataSetChanged();
    }

    class ViewHolder {

        TextView tvUserName;
        ImageView ivCheckBox;

    }
}
