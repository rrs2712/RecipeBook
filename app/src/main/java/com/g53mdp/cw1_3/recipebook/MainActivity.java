package com.g53mdp.cw1_3.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter dataAdapter;

    private final String
        CLA = "RRS01 MainActivity",
        NO_DATA_MSG = "Wow! Your recipe book is empty. \n\n Add a new recipe.";

    public static String
        TAG_ACTION = "actionToDo",
        ITEM_ID = "itemId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(CLA,"onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(CLA,"onStart");
        setWidgets();
    }

    private void setWidgets() {

        String[] projection = new String[]{
            MyProviderContract._ID,
            MyProviderContract.TITLE
        };

        String[] colsToDisplay = new String[]{
            MyProviderContract._ID,
            MyProviderContract.TITLE
        };

        int[] colResIDs = new int[]{
            R.id.idView,
            R.id.titleView
        };

        Cursor cursor = getContentResolver().query(MyProviderContract.RECIPE_URI,projection,null,null,null);

        dataAdapter = new SimpleCursorAdapter(this, R.layout.db_item_layout,cursor, colsToDisplay, colResIDs,0);

        final ListView lv = (ListView) findViewById(R.id.lv_recipes);
        lv.setAdapter(dataAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter,
                                    View myView,
                                    int myItemInt,
                                    long mylng) {
                Cursor c1 = (Cursor) lv.getAdapter().getItem(myItemInt);
                showRecipeDetails(c1.getInt(0));
            }
        });

    }

    private void showRecipeDetails(int selectedItem) {
        Log.d(CLA," Selected item: " + selectedItem);

        Bundle b = new Bundle();
        b.putInt(TAG_ACTION,2);
        b.putInt(ITEM_ID,selectedItem);

        Intent i = new Intent(MainActivity.this, CrudActivity.class);
        i.putExtras(b);

        startActivity(i);
    }

    public void onAddBtn(View view){
        Log.d(CLA,"onAddBtn");

        Bundle b = new Bundle();
        b.putInt(TAG_ACTION,1);

        Intent i = new Intent(MainActivity.this, CrudActivity.class);
        i.putExtras(b);

        startActivity(i);
    }

}
