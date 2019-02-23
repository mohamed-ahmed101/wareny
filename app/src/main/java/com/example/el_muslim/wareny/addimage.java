package com.example.el_muslim.wareny;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import com.example.el_muslim.wareny.helper.HttpJsonParser;

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
           String uploadImage = getStringImage(BitmapFactory.decodeResource(context.getResources(),R.drawable.addimage));
        String data = null;
        try {
            data = URLEncoder.encode("image_name", "UTF-8")
                    + "=" + URLEncoder.encode(uploadImage, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            data += "&" + URLEncoder.encode("item_id", "UTF-8") + "="
                    + URLEncoder.encode("13", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {

            // Defined URL  where to send data
            URL url = new URL(BASE_URL + "save_image_to_database.php");

            // Send POST data request

            URLConnection conn = (HttpURLConnection) url.openConnection();
            ((HttpURLConnection) conn).setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
        } catch(Exception ex)
        {

        }

      /*  String uploadImage = getStringImage(BitmapFactory.decodeResource(context.getResources(),R.drawable.shoes));
        String id = "13";
        HttpJsonParser httpJsonP = new HttpJsonParser();
        HashMap<String,String> data = new HashMap<>();
         data.put(KEY_SUCCESS, uploadImage);
          data.put(KEY_CATEGORY_ID,id);
        JSONObject jsonO = httpJsonP.makeHttpRequest(BASE_URL + "save_image_to_database.php","POST" ,data );*/
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
