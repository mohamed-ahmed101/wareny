package com.example.el_muslim.wareny.imagehelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.example.el_muslim.wareny.LoginActivity;
import com.example.el_muslim.wareny.helper.HttpJsonParser;
import com.example.el_muslim.wareny.itemDetails;
import com.kosalgeek.android.photoutil.ImageBase64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class fetchitemimages extends AsyncTask<String , String , String> {

    Context context ;

    private static final String KEY_DATA = "data";
    private static final String KEY_ITEM_ID = "item_id";
    private static final String KEY_IMAGE_SRC = "image_src";
    String itemID ;

    private static final String KEY_SUCCESS = "success";


    private static final String BASE_URL = LoginActivity.UserLoginTask.BASE_URL;

    private int success;
    private ProgressDialog pDialog;




    public  fetchitemimages(Context c,String IID)
    {
        context = c;
        itemID = IID;

    }

    @Override
    protected String doInBackground(String... strings) {

        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String , String> map = new HashMap<String, String>() ;
        map.put(KEY_ITEM_ID,itemID);
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "fetch_all_images_of_items.php","GET" ,map );

        try {
            success = jsonObject.getInt(KEY_SUCCESS);
            JSONArray items;
            if(success==1)
            {
                items=jsonObject.getJSONArray(KEY_DATA);

                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    String imageSrc = item.getString("image_src");
                    byte[] decodedString = Base64.decode(imageSrc, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    itemDetails.arrayList.add(decodedByte);
                    itemDetails.imagesName.add(item.getString("image_name"));
                }
                itemDetails.imgadapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Adding Item. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        pDialog.dismiss();
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (success == 1)
                {
                    Toast.makeText(context,
                            "Images Loaded Successfully", Toast.LENGTH_LONG).show();
                  //  itemDetails.imgadapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(context,
                            "No images for this item",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
