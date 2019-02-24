package com.example.el_muslim.wareny;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Base64;

import com.example.el_muslim.wareny.helper.HttpJsonParser;
import com.kosalgeek.android.photoutil.ImageBase64;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import static com.example.el_muslim.wareny.LoginActivity.UserLoginTask.BASE_URL;

public class addimage extends AsyncTask<String , String , String> {
Context context ;
    private static final String KEY_SUCCESS = "image_name";
    private static final String KEY_CATEGORY_ID = "item_id";



     public  addimage(Context c)
 {
     this.context = c;
 }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    protected String doInBackground(String... strings) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.shoes);

        String s = ImageBase64.encode(bitmap);

        HttpJsonParser httpJsonP = new HttpJsonParser();



        HashMap<String,String> data = new HashMap<>();
        data.put("image_src", s);
        JSONObject jsonO = httpJsonP.makeHttpRequest(BASE_URL + "save_image_to_database.php","POST" ,data );





         int x =0;
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
