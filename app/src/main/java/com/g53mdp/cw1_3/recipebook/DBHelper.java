package com.g53mdp.cw1_3.recipebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rrs27 on 2016-12-12.
 */

public class DBHelper extends SQLiteOpenHelper {

    private final String CLA = "RRS03 DBHelper";
    public static final String
            KEY_ROWID = "_id",
            KEY_TITLE = "title",
            KEY_INSTRUCS = "instructions",
            KEY_IMAGE =  "image",
            DB_NAME = "RecipeBookDB",
            TABLE_NAME = "userRecipe";
    public static final int
            DB_VERSION = 1;
    private final String
            CREATE_TABLE = "CREATE TABLE if not exists " + TABLE_NAME + " (" +
            KEY_ROWID + " integer PRIMARY KEY autoincrement," +
            KEY_TITLE + " TEXT(100)," +
            KEY_INSTRUCS + " TEXT(1000)," +
            KEY_IMAGE + " TEXT(1000)" +
            ");";
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

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d(CLA,"DBHelper instantiated");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(CLA, "onCreate");

        db.execSQL(CREATE_TABLE);
        Log.d(CLA, "DB Created");

        db.execSQL(DEFAULT_RECIPE);
//        db.execSQL(DEFAULT_RECIPE);
//        db.execSQL(DEFAULT_RECIPE);
//        db.execSQL("delete from " + TABLE_NAME + " where _id=2");
        Log.d(CLA, "Default recipe added");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(CLA, "onUpgrade");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
