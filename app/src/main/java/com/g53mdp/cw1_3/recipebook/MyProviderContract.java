package com.g53mdp.cw1_3.recipebook;

import android.net.Uri;

/**
 * Created by rrs27 on 2016-12-12.
 */

public class MyProviderContract {

    public static final String AUTHORITY = "com.g53mdp.cw1_3.recipebook.MyProvider";

    public static final Uri RECIPE_URI = Uri.parse("content://"+AUTHORITY+"/recipe");
    public static final Uri RECIPE_URI_ID = Uri.parse("content://"+AUTHORITY+"/recipe/#");

    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String INSTR = "instructions";
    public static final String IMG = "image";

    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/MyProvider.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/MyProvider.data.text";

}
