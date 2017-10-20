package com.android.tutotials.facebooklogintest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by oussamakaoui on 8/18/17.
 */

public class AlbumListAdapter extends BaseAdapter {

    private Context context;
    private List<FacebookAlbum> albumItems;


    public AlbumListAdapter(Context context, List<FacebookAlbum> albumItems) {
        this.context = context;
        this.albumItems = albumItems;
    }

    @Override
    public int getCount() {
        return albumItems.size();
    }

    @Override
    public Object getItem(int position) {
        return albumItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FacebookAlbum fbAlbum = albumItems.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        convertView = mInflater.inflate(R.layout.album_list_item,
                    null);

        Typeface textStyle = Typeface.createFromAsset(context.getAssets(),  "fonts/FBLight-Regular.ttf");

        TextView albumNameTV = (TextView) convertView.findViewById(R.id.album_name);
        TextView albumCreatedTimeTV = (TextView) convertView.findViewById(R.id.album_created_date);


        albumNameTV.setText(fbAlbum.getName());
        albumCreatedTimeTV.setText(fbAlbum.getCreatedTime().toString());

        albumNameTV.setTypeface(textStyle);
        albumCreatedTimeTV.setTypeface(textStyle);


        return convertView;
    }

}
