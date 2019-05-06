package ir.mrdota.touristplace.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import ir.mrdota.touristplace.models.model_main_menu;

import static android.content.Context.MODE_PRIVATE;

public class opendb {


    private Context context;
    private String dbName = "tdb";
    SQLiteDatabase db ;
    String DBNAME="tdb";
    String DBTABLE="tdb";

    public opendb(Context context) {
        this(context, "tdb");
    }

    public opendb(Context context, String dbName) {
        this.context = context;
        this.dbName = dbName;
    }

    public void checkDb() {
        File dbfile = context.getDatabasePath(dbName);
        if (!dbfile.exists()) {
            try {
                copyDatabase(dbfile);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public void copyDatabase(File dbfile) throws IOException {
        InputStream is = context.getAssets().open("tdb.db");
        dbfile.getParentFile().mkdirs();
        OutputStream os = new FileOutputStream(dbfile);

        int len = 0;
        byte[] buffer = new byte[1024];

        while ((len = is.read(buffer)) > 0) {
            os.write(buffer, 0, len);
        }

        os.flush();
        os.close();
        is.close();
    }
    public ArrayList<model_main_menu> maindet(int i,String search ,String where,Context context) {
        ArrayList<model_main_menu> result = new ArrayList<>();
        Cursor cursor = null;
        try {


            new opendb(context ,"tdb").checkDb();

            db = context.openOrCreateDatabase(DBNAME,MODE_PRIVATE,null);




            if (i ==1){/**              FOR DETAILS                  */
              //  cursor = db.query(DBTABLE, new String[]{"_id","category","title", "text","src_img"}, null, null, null, null, null);
                cursor = db.rawQuery("SELECT   *  FROM tdb  where category = '"+where+"'",null);

                if (cursor != null && cursor.moveToFirst()) {
                    do {

                        result.add(new model_main_menu(
                                        cursor.getInt(cursor.getColumnIndex("_id"))
                                        , cursor.getString(cursor.getColumnIndex("category"))
                                        , cursor.getString(cursor.getColumnIndex("title"))
                                        , cursor.getString(cursor.getColumnIndex("text"))
                                        , cursor.getString(cursor.getColumnIndex("src_img"))
                                ,null
                                ,cursor.getInt(cursor.getColumnIndex("fav"))
                                )
                        );

                    } while (cursor.moveToNext());
                }
            }






            if (i ==0){/**              FOR CATEGORY                  */
                cursor = db.rawQuery("SELECT DISTINCT category,src_ic_img  FROM tdb ",null);

                if (cursor != null && cursor.moveToFirst()) {
                    do {

                        result.add(new model_main_menu(
                                        0
                                        , cursor.getString(cursor.getColumnIndex("category"))
                                        , null
                                        , null
                                        ,null
                                        ,cursor.getString(cursor.getColumnIndex("src_ic_img"))
                                ,0
                                )
                        );


                    } while (cursor.moveToNext());
                }
            }







            if (i==2){/**              FOR category:2 title:3 text:4 SEARCH                  */

                cursor = db.rawQuery("SELECT DISTINCT category,src_ic_img  FROM tdb WHERE category LIKE '%"+search+"%'",null);

                if (cursor != null && cursor.moveToFirst()) {
                    do {

                        result.add(new model_main_menu(
                                        0
                                        , highlight(cursor.getString(cursor.getColumnIndex("category")).toLowerCase(),search)
                                        , null
                                        , null
                                        ,null
                                        ,cursor.getString(cursor.getColumnIndex("src_ic_img"))
                                ,0
                                )
                        );


                    } while (cursor.moveToNext());
                }
            }










            if (  i ==3 || i==4 ){/**              FOR category:2 title:3 text:4 SEARCH                  */
                 if (i==3)
                    cursor = db.rawQuery("SELECT    *    FROM tdb WHERE title LIKE '%"+search+"%'",null);
                else if (i==4)
                    cursor = db.rawQuery("SELECT    *    FROM tdb WHERE text  LIKE '%"+search+"%'",null);


                if (cursor != null && cursor.moveToFirst()) {
                    do {





                        if (i==3)
                            result.add(new model_main_menu(
                                            cursor.getInt(cursor.getColumnIndex("_id"))
                                            , cursor.getString(cursor.getColumnIndex("category")).toLowerCase()
                                            , highlight(cursor.getString(cursor.getColumnIndex("title")).toLowerCase(),search)
                                            , cursor.getString(cursor.getColumnIndex("text")).toLowerCase()
                                            , cursor.getString(cursor.getColumnIndex("src_img"))
                                            , cursor.getString(cursor.getColumnIndex("src_ic_img"))
                                    ,cursor.getInt(cursor.getColumnIndex("fav"))
                                    )
                            );

                        if (i==4)
                            result.add(new model_main_menu(
                                            cursor.getInt(cursor.getColumnIndex("_id"))
                                            , cursor.getString(cursor.getColumnIndex("category")).toLowerCase()
                                            , cursor.getString(cursor.getColumnIndex("title")).toLowerCase()
                                            , highlight(cursor.getString(cursor.getColumnIndex("text")).toLowerCase(),search)
                                            , cursor.getString(cursor.getColumnIndex("src_img"))
                                            , cursor.getString(cursor.getColumnIndex("src_ic_img"))
                                    ,cursor.getInt(cursor.getColumnIndex("fav"))
                                    )
                            );


                    } while (cursor.moveToNext());
                }
            }


            if (i==5) {/****               FOR GET FAV               *********/

                cursor = db.rawQuery("SELECT    *    FROM tdb WHERE fav = 1",null);


                if (cursor != null && cursor.moveToFirst()) {
                    do {

                            result.add(new model_main_menu(
                                    cursor.getInt(cursor.getColumnIndex("_id"))
                                    , cursor.getString(cursor.getColumnIndex("category"))
                                    , cursor.getString(cursor.getColumnIndex("title"))
                                    , cursor.getString(cursor.getColumnIndex("text"))
                                    , cursor.getString(cursor.getColumnIndex("src_img"))
                                    ,null
                                    ,cursor.getInt(cursor.getColumnIndex("fav"))
                                    )
                            );


                    } while (cursor.moveToNext());
                }



            }



















        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return result;
    }
    String highlight(String alltxt , String txt_search) {
        String A = alltxt;
        if (A.contains(txt_search.toLowerCase()))
            A = A.replaceAll(txt_search.toLowerCase(), "" + "<font color='red'>" + txt_search.toLowerCase() + "</font>" + "");
        return A;

    }
    public void update_fav(int fav,int _id ){


        try {

            db = context.openOrCreateDatabase(DBNAME,MODE_PRIVATE,null);


             db.execSQL("UPDATE tdb SET fav = '"+fav+"' WHERE _id  = '"+_id+"'" );

        }catch (Exception e ){

        }




    }


}

