package com.bowenzhang.contact;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bowenzhang on 15/11/14.
 */
public class ContactAdapter extends BaseAdapter {

    private Context context;
    private List<ContactInfo> contacts;

    public ContactAdapter(Context context, List<ContactInfo> contacts) {
        this.context = context;
        this.contacts = contacts;
    }


    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public ContactInfo getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_contact, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactInfo contact = getItem(position);
        holder.tv_name.setText(contact.getName());
        holder.tv_phone.setText(contact.getPhone());
        return convertView;
    }

    static class ViewHolder{
        TextView tv_name;
        TextView tv_phone;
    }

}
