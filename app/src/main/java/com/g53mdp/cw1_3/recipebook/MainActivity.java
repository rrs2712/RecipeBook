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

    private RecipeDBAdapter dbAdapter;
    private SimpleCursorAdapter dataAdapter;

    private final String
        CLA = "RRS01 MainActivity",
        NO_DATA_MSG = "Wow! Your recipe book is empty. \n\n Add a new recipe.",
        TAG_ACTION = "actionToDo";

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

        dbAdapter = new RecipeDBAdapter(this);
        dbAdapter.open();

        setWidgets();
    }

    private void setWidgets() {


        final Cursor cursor = dbAdapter.db.query(
            RecipeDBAdapter.TABLE_NAME,
            new String[]{
                RecipeDBAdapter.KEY_ROWID,
                RecipeDBAdapter.KEY_TITLE
            },
            null,null,null,null,null);

        String[] columns = new String[]{
            RecipeDBAdapter.KEY_ROWID,
            RecipeDBAdapter.KEY_TITLE
        };

        int[] to = new int[]{
            R.id.idView,
            R.id.titleView
        };

        dataAdapter = new SimpleCursorAdapter(this, R.layout.db_item_layout,cursor, columns, to,0);

        final ListView lv = (ListView) findViewById(R.id.lv_recipes);
        lv.setAdapter(dataAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter,
                                    View myView,
                                    int myItemInt,
                                    long mylng) {
                //String itemSelected = (String) (lv.getItemAtPosition(myItemInt));
                Cursor c1 = (Cursor) lv.getAdapter().getItem(myItemInt);
                Log.d(CLA,"" + c1.getInt(0));


                //Do something with each recipe here
                //showRecipeDetails(itemSelected);
            }
        });

    }

    private void showRecipeDetails(String selectedFromList) {
        Toast.makeText(this,selectedFromList,Toast.LENGTH_SHORT).show();
    }

    public void onAddBtn(View view){
        Log.d(CLA,"onAddBtn");

        Bundle b = new Bundle();
        b.putInt(TAG_ACTION,1);

        Intent i = new Intent(MainActivity.this, CrudActivity.class);
        i.putExtras(b);

        startActivity(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(CLA,"onStop");

        dbAdapter.close();
    }
}
