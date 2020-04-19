package gupta.yohansh.justask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class favhelper extends SQLiteOpenHelper {

    private static final String Databasename = "Favorites.db";
    private static final String tablename = "favoritestable";
    private static final String favnum = "id";
    private static final String favtitle = "title";
    private static final String favdescription = "description";


    public favhelper(@Nullable Context context) {
        super(context, Databasename, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tablename + "(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT ,description TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + tablename);
        onCreate(db);

    }

    public void addfav(model art) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(favtitle, art.getQuestiontopic());
        contentValues.put(favdescription, art.getQuestion());
        db.insert(tablename, null, contentValues);

    }

    public List<model> getfav() {
        String[] columns = {
                favtitle, favdescription
        };
        List<model> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(tablename, columns, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                model art = new model();
                art.setQuestiontopic(c.getString(c.getColumnIndex(favtitle)));
                art.setQuestion(c.getString(c.getColumnIndex(favdescription)));
                modelList.add(art);

            } while (c.moveToNext());


        }
        return modelList;
    }
}
