package com.example.task71lostandfoundapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdvertAdapter extends BaseAdapter {

    Context context;
    ArrayList<Advert> adverts;

    public AdvertAdapter(Context context, ArrayList<Advert> adverts) {
        this.context = context;
        this.adverts = adverts;
    }

    @Override
    public int getCount() {
        return adverts.size();
    }

    @Override
    public Object getItem(int position) {
        return adverts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return adverts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_advert,
                    parent,
                    false
            );
        }

        ImageView imgItem = convertView.findViewById(R.id.imgItem);
        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        TextView txtCategory = convertView.findViewById(R.id.txtCategory);
        TextView txtCreatedAt = convertView.findViewById(R.id.txtCreatedAt);

        Advert advert = adverts.get(position);

        txtTitle.setText(advert.getPostType() + ": " + advert.getName());
        txtCategory.setText("Category: " + advert.getCategory());
        txtCreatedAt.setText("Posted: " + advert.getCreatedAt());

        imgItem.setImageURI(Uri.parse(advert.getImageUri()));

        // Opens the detail screen when user selects an advert
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra("advert_id", advert.getId());
            context.startActivity(intent);
        });

        return convertView;
    }
}
