package com.example.el_muslim.wareny;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class customAdapter extends BaseAdapter {


        Context context;
        ArrayList<String> data =new ArrayList<String>();
        ArrayList<Bitmap> image = new ArrayList<Bitmap>();
        private static LayoutInflater inflater = null;

        public customAdapter(Context context, ArrayList<String> data,  ArrayList<Bitmap> image) {
            // TODO Auto-generated constructor stub
            this.image = image;
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.customrow, null);
        TextView text = (TextView) vi.findViewById(R.id.textView);
        text.setText(data.get(position));

        ImageView imageView = (ImageView) vi.findViewById(R.id.imageView);

        imageView.setImageBitmap(image.get(position));


        return vi;
    }



}
