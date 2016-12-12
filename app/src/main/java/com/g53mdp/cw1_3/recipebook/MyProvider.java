package com.g53mdp.cw1_3.recipebook;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static android.R.attr.id;

/**
 * Created by rrs27 on 2016-12-12.
 */

public class MyProvider extends ContentProvider
{
    private DBHelper dbHelper = null;
    private final String CLA = "RRS04 MyProvider";

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MyProviderContract.AUTHORITY, "recipe", 1);
        uriMatcher.addURI(MyProviderContract.AUTHORITY, "recipe/#", 2);
    }

    @Override
    public boolean onCreate() {
        Log.d(CLA, "contentProvider onCreate");
        this.dbHelper = new DBHelper(this.getContext(), DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
        return true;
    }

    @Override
    public String getType(Uri uri) {

        String contentType;

        if (uri.getLastPathSegment()==null)
        {
            contentType = MyProviderContract.CONTENT_TYPE_MULTIPLE;
        }
        else
        {
            contentType = MyProviderContract.CONTENT_TYPE_SINGLE;
        }

        return contentType;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName;

        switch(uriMatcher.match(uri))
        {
            case 1:
            default:
                tableName = DBHelper.TABLE_NAME;
                break;
        }

        long id = db.insert(tableName, null, values);
        db.close();
        Uri nu = ContentUris.withAppendedId(uri, id);

        Log.d(CLA, nu.toString());

        getContext().getContentResolver().notifyChange(nu, null);

        return nu;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.d(CLA, uri.toString() + " " + uriMatcher.match(uri));

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch(uriMatcher.match(uri))
        {
            case 2:
                selection = "_id = " + uri.getLastPathSegment();
            case 1:
                return db.query(DBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            default:
                return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName;

        switch(uriMatcher.match(uri))
        {
            case 1:
            default:
                tableName = DBHelper.TABLE_NAME;
                break;
        }

        int a = db.update(tableName,values,selection,selectionArgs);
        db.close();

        Log.d(CLA, "update");

        return a;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName;

        switch(uriMatcher.match(uri))
        {
            case 1:
            default:
                tableName = DBHelper.TABLE_NAME;
                break;
        }

        int a = db.delete(tableName,selection,selectionArgs);
        db.close();

        Log.d(CLA, "delete");

        return a;
    }

}