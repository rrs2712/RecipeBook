package com.g53mdp.cw1_3.recipebook;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.name;

/**
 * Created by rrs27 on 2016-12-11.
 */

public class RecipeDBAdapter {

    private final String CLA = "RRS02 RecipeDBAdapter";
    public static final String
        KEY_ROWID = "_id",
        KEY_TITLE = "title",
        KEY_INSTRUCS = "instructions",
        KEY_IMAGE =  "image",
        DB_NAME = "RecipeBookDB",
        TABLE_NAME = "userRecipe";
    private static final int
        SQLITE_DB_VERSION = 1;
    private static final String
        SQLITE_CREATE = "CREATE TABLE if not exists " + TABLE_NAME + " (" +
            KEY_ROWID + " integer PRIMARY KEY autoincrement," +
            KEY_TITLE + " TEXT(100)," +
            KEY_INSTRUCS + " TEXT(1000)," +
            KEY_IMAGE + " TEXT(1000)" +
            ");";

    private DatabaseHelper dbHelper;
    public SQLiteDatabase db;
    private Context context;

    public RecipeDBAdapter(Context context) {
        this.context = context;
        Log.d(CLA,"instantiated");
    }

    public RecipeDBAdapter open() throws SQLException {
        Log.d(CLA,"open()");
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        Log.d(CLA,"close()");
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public void insertRecipe(String title, String instructions)
    {
        String newRecipe = "INSERT INTO " + TABLE_NAME + " (" +
            KEY_TITLE + "," + KEY_INSTRUCS + "," + KEY_IMAGE +  ") VALUES ('" +
            title + "','" + instructions + "',null);"
            ;

        db.execSQL(newRecipe);
    }

    public Cursor fetchAll()
    {
        Cursor c = db.query("myList", new String[] { "_id", "name", "colour" }, null, null, null, null, null);
        return c;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private final String CLA = "RRS03 DatabaseHelper";
        private final String
                DEF_TITLE = "'Sunday Roast'",
                DEF_INSTRUCS =
                    "'1) Remove the beef from the fridge 30 minutes before you want to cook it, to let it come up to room temperature.\n" +
                    "2) Preheat the oven to 240 C - 475 F\n" +
                    "3) Wash and roughly chop the vegetables – there’s no need to peel them. Break the garlic bulb into cloves, leaving them unpeeled.\n" +
                    "4) Pile all the veg, garlic and herbs into the middle of a large roasting tray and drizzle with oil.'",
                DEF_IMAGE =  null;
        private final String
                DEFAULT_RECIPE = "INSERT INTO " + TABLE_NAME + " (" +
                KEY_TITLE + "," + KEY_INSTRUCS + "," + KEY_IMAGE +  ") VALUES (" +
                DEF_TITLE + "," + DEF_INSTRUCS + "," + DEF_IMAGE +  ");"
                ;

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, SQLITE_DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(CLA, "onCreate");

            db.execSQL(SQLITE_CREATE);
            Log.d(CLA, "DB Created");

            db.execSQL(DEFAULT_RECIPE);
            db.execSQL(DEFAULT_RECIPE);
            db.execSQL(DEFAULT_RECIPE);
            db.execSQL("delete from " + TABLE_NAME + " where _id=2");
            Log.d(CLA, "Default recipe added");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(CLA, "onUpgrade");

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
