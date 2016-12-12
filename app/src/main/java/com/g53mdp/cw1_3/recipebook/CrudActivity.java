package com.g53mdp.cw1_3.recipebook;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.R.attr.name;

public class CrudActivity extends AppCompatActivity {

    private final String
        CLA = "RRS04 CrudActivity",
        SAVED_MSG = "Recipe saved!";

    private int itemID;
    private String itemTitle, itemDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        Log.d(CLA,"onCreate");

        setWidgets();
    }

    private void setWidgets(){
        Bundle bundle = getIntent().getExtras();
        int layoutResolver = bundle.getInt(MainActivity.TAG_ACTION);

        LinearLayout saveLayout = (LinearLayout) findViewById(R.id.lin_lay_save);
        LinearLayout updateLayout = (LinearLayout) findViewById(R.id.lin_lay_update);
        EditText aTitle = (EditText) findViewById(R.id.et_recipe_title);
        EditText aDesc = (EditText) findViewById(R.id.et_recipe_desc);

        switch (layoutResolver){
            case 1:
                updateLayout.setVisibility(View.GONE);
                break;
            case 2:
                saveLayout.setVisibility(View.GONE);

                itemID = bundle.getInt(MainActivity.ITEM_ID);
                String[] projection = new String[]{
                        MyProviderContract._ID,
                        MyProviderContract.TITLE,
                        MyProviderContract.INSTR
                };

                String selection = "_id = " + itemID;

                Cursor cursor = getContentResolver().query(
                    MyProviderContract.RECIPE_URI_ID,
                    projection,
                    selection,null,null);

                Log.d(CLA,cursor.toString() );

                if(cursor.moveToFirst())
                {
                    do
                    {
                        itemTitle = cursor.getString(1);
                        itemDesc = cursor.getString(2);
                        Log.d(CLA,itemTitle + "----" + itemDesc );
                    }
                    while(cursor.moveToNext());
                }

                aTitle.setText(itemTitle);
                aDesc.setText(itemDesc);

                break;
        }
    }

    public void onSaveBtn(View view){
        Log.d(CLA,"onSaveBtn");

        EditText aTitle = (EditText) findViewById(R.id.et_recipe_title);
        EditText aDesc = (EditText) findViewById(R.id.et_recipe_desc);

        ContentValues newValues = new ContentValues();
        newValues.put(MyProviderContract.TITLE, aTitle.getText().toString());
        newValues.put(MyProviderContract.INSTR, aDesc.getText().toString());

        getContentResolver().insert(MyProviderContract.RECIPE_URI, newValues);

        Toast.makeText(this,SAVED_MSG,Toast.LENGTH_LONG).show();

        finish();
    }

    public void onAddImgBtn(View view){
        Toast.makeText(this,"Does nothing yet",Toast.LENGTH_SHORT).show();
    }

    public void onDeleteBtn(View view){
        String selection = "_id = " + itemID;

        getContentResolver().delete(
                MyProviderContract.RECIPE_URI,
                selection,null);

        Toast.makeText(this,"recipe deleted",Toast.LENGTH_SHORT).show();

        finish();
    }

    public void onUpdateBtn(View view){
        String selection = "_id = " + itemID;

        EditText aTitle = (EditText) findViewById(R.id.et_recipe_title);
        EditText aDesc = (EditText) findViewById(R.id.et_recipe_desc);

        ContentValues newValues = new ContentValues();
        newValues.put(MyProviderContract.TITLE, aTitle.getText().toString());
        newValues.put(MyProviderContract.INSTR, aDesc.getText().toString());

        getContentResolver().update(
                MyProviderContract.RECIPE_URI,
                newValues,
                selection,
                null);

        Toast.makeText(this,"recipe updated",Toast.LENGTH_SHORT).show();

        finish();
    }

}
