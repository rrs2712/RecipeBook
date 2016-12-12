package com.g53mdp.cw1_3.recipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CrudActivity extends AppCompatActivity {

    private final String
        CLA = "RRS04 CrudActivity",
        SAVED_MSG = "Recipe saved!";

    private RecipeDBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        Log.d(CLA,"onCreate");

        dbAdapter = new RecipeDBAdapter(this);
        dbAdapter.open();
    }

    public void onSaveBtn(View view){
        Log.d(CLA,"onSaveBtn");

        EditText aTitle = (EditText) findViewById(R.id.et_recipe_title);
        EditText aDesc = (EditText) findViewById(R.id.et_recipe_desc);

        dbAdapter.insertRecipe(aTitle.getText().toString(),aDesc.getText().toString());

        Toast.makeText(this,SAVED_MSG,Toast.LENGTH_LONG).show();

        finish();
    }

    public void onAddImgBtn(View view){
        Toast.makeText(this,"Does nothing yet",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(CLA,"onStop");

        dbAdapter.close();
    }
}
