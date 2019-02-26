package com.example.el_muslim.wareny;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.el_muslim.wareny.categorymodel.AllCategoryFetch;
import com.example.el_muslim.wareny.categorymodel.CategoryHelperAdd;
import com.example.el_muslim.wareny.categorymodel.updateOrDeleteCategory;
import com.example.el_muslim.wareny.helper.HttpJsonParser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static com.example.el_muslim.wareny.LoginActivity.UserLoginTask.BASE_URL;

public class storeActivity extends AppCompatActivity {

   ListView categoriesLst;
   public  static   ArrayList<String> categoriesName;
   public static   ArrayList<String> categoriesId;
    ArrayList<Bitmap> images;
  public static   customAdapter arrayAdapter;
    ImageView imageView;
    EditText editText ;
    CategoryHelperAdd categoryHelper;

    int sup_id ;
    String supName;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(!customerActivity.USER) {
            getMenuInflater().inflate(R.menu.addcategory, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addCategory) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Add New Category");

            View v = LayoutInflater.from(storeActivity.this).inflate(R.layout.dialoglayout,null);
            alert.setView(v);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            editText = (EditText) v.findViewById(R.id.textView) ;

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
                    if(!editText.getText().toString().isEmpty() ) {
                        categoriesName.add(editText.getText().toString());
                        categoryHelper = new CategoryHelperAdd(storeActivity.this);
                        categoryHelper.AddCategory(editText.getText().toString(),String.valueOf(sup_id));
                        images.add(((BitmapDrawable)imageView.getDrawable()).getBitmap());
                        arrayAdapter.notifyDataSetChanged();
                    }
                    else  {
                        Toast.makeText(getApplicationContext(),"Enter Category Name",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alert.create();
            alert.show();
        }
                return true ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        final Intent intent = getIntent();
        sup_id = intent.getIntExtra("supplierID", 0);
        supName = intent.getStringExtra("supplierName");
        getSupportActionBar().setTitle(supName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        images = new ArrayList<Bitmap>();
        categoryHelper = new CategoryHelperAdd(storeActivity.this);
        categoriesLst = (ListView) findViewById(R.id.categoriesListView);
        categoriesName = new ArrayList<String>();
        categoriesId = new ArrayList<String>();

        try {
            String r = new AllCategoryFetch(storeActivity.this, sup_id).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < categoriesName.size(); i++) {
            images.add(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.addimage));
        }

        // this.categoriesName = AllCategoryFetch.categoriesName;
        // this.categoriesId = AllCategoryFetch.categoriesId;
        arrayAdapter = new customAdapter(this, categoriesName, images);
        categoriesLst.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        //click to add items
        categoriesLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), catItemsActivity.class);

                intent.putExtra("categoryName", categoriesName.get(position));
                intent.putExtra("categoryId", categoriesId.get(position));

                startActivity(intent);


            }
        });


        //long click to update or delete
        if(!customerActivity.USER) {
        categoriesLst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(storeActivity.this);

                View v = LayoutInflater.from(storeActivity.this).inflate(R.layout.dialoglayout, null);
                alert.setView(v);

                imageView = (ImageView) v.findViewById(R.id.imageView);
                editText = (EditText) v.findViewById(R.id.textView);

                imageView.setImageBitmap(images.get(position));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, 0);
                    }
                });

                editText.setText(categoriesName.get(position));
                editText.setSelection(editText.getText().length());

                alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        categoriesName.set(position, editText.getText().toString());
                        // images.set(position,((BitmapDrawable)imageView.getDrawable()).getBitmap());

                        new updateOrDeleteCategory(storeActivity.this, categoriesName.get(position), categoriesId.get(position)).execute();

                        arrayAdapter.notifyDataSetChanged();
                    }
                });

                alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        categoriesName.remove(position);
                        new updateOrDeleteCategory(storeActivity.this, "", categoriesId.get(position)).execute();
                        categoriesId.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });


                alert.setTitle("Edit");
                alert.create();
                alert.show();
                return true;
            }
        });

    }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(storeActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(storeActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}


