package com.example.el_muslim.wareny;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.el_muslim.wareny.itemsmodel.addItem;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class catItemsActivity extends AppCompatActivity {

    ListView itemsList;
    ArrayList<String> items;
    ArrayList<Bitmap> images;
    customAdapter arrayAdapter;
    ImageView imageView;
    EditText editText;
    FloatingActionButton button;
    String categoryName="";
    String categoryId="";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.addcategory, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_items);


        final Intent intent = getIntent();
        categoryName= intent.getStringExtra("categoryName");
        categoryId = intent.getStringExtra("categoryId");
        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        images = new ArrayList<Bitmap>();
        items = new ArrayList<String>();
        arrayAdapter = new customAdapter(getApplicationContext(),items,images);
        itemsList = (ListView) findViewById(R.id.lstviewitem);
        button = (FloatingActionButton) findViewById(R.id.additemsbutton);
        itemsList.setAdapter(arrayAdapter);


        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startActivity(new Intent(getApplicationContext(),itemDetails.class));

            }
        });

       itemsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
               AlertDialog.Builder builder = new AlertDialog.Builder(catItemsActivity.this);
               builder.setTitle("Are You Sure To Delete This Item");
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       items.remove(position);
                       arrayAdapter.notifyDataSetChanged();
                   }
               });
               builder.setNegativeButton("no",null);
               builder.create();
               builder.show();
               return true;
           }
       });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(catItemsActivity.this);
                alert.setTitle("Add New Item");
                View vv = LayoutInflater.from(catItemsActivity.this).inflate(R.layout.dialoglayout, null);
                alert.setView(vv);
                imageView = (ImageView) vv.findViewById(R.id.imageView);
                editText = (EditText) vv.findViewById(R.id.textView);
                imageView.setImageResource(R.drawable.addimage);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent,0);
                    }
                });
                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editText.getText().toString().isEmpty()) {
                            items.add(editText.getText().toString());
                            images.add(((BitmapDrawable) imageView.getDrawable()).getBitmap());
                            new addItem(catItemsActivity.this,editText.getText().toString(),categoryId).execute();

                            arrayAdapter.notifyDataSetChanged();
                            Snackbar.make(v, "Item Added", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter Category Name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.create();
                alert.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(catItemsActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(catItemsActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}




  //  Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  //  startActivityForResult(takePicture, 0);//zero can be replaced with any action code


  //  Intent pickPhoto = new Intent(Intent.ACTION_PICK,
  //          android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
 //   startActivityForResult(pickPhoto , 1);//one can be replaced with any action code




  //  protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
    //    super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    //    switch(requestCode) {
     //       case 0:
        //        if(resultCode == RESULT_OK){
       //  /           Uri selectedImage = imageReturnedIntent.getData();
           //         imageview.setImageURI(selectedImage);
          //      }

           //     break;
         //   case 1:
          //      if(resultCode == RESULT_OK){
          //          Uri selectedImage = imageReturnedIntent.getData();
          //          imageview.setImageURI(selectedImage);
          //      }
          //      break;
      //  }
   // }