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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class deleteitemimage extends AsyncTask<String , String , String> {

    private static final String KEY_DATA = "data";
    private static final String KEY_ITEM_ID = "item_id";
    private static final String KEY_IMAGE_NAME = "image_name";
    String itemID ;
    String imageName;

    private static final String KEY_SUCCESS = "success";


    private static final String BASE_URL = LoginActivity.UserLoginTask.BASE_URL;
    Context context ;
    private int success;
    private ProgressDialog pDialog;

    public  deleteitemimage (Context c, String IID,String imageName)
    {
        context = c;
        itemID = IID;
        this.imageName = imageName;
    }
    @Override
    protected String doInBackground(String... strings) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String , String> map = new HashMap<String, String>() ;
        map.put(KEY_ITEM_ID,itemID);
        map.put(KEY_IMAGE_NAME,imageName);
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "delete_item_image.php","POST" ,map );

        try {
            success = jsonObject.getInt(KEY_SUCCESS);
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
                if (success == 1 )
                {
                    Toast.makeText(context,
                            "Item Image deleted successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context,
                            "Action Not Occured On DataBase",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
