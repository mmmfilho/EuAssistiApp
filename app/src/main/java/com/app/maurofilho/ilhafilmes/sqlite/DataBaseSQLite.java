package com.app.maurofilho.ilhafilmes.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import com.app.maurofilho.ilhafilmes.model.Filme;


public class DataBaseSQLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SQLiteDBEuAssisti.db";
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    //TABELA FILME
    private static final String FILME_TABLE_NAME = "filme";
    public static final String FILME_COLUMN_ID = "id";
    public static final String FILME_COLUMN_TITLE = "title";
    public static final String FILME_COLUMN_GENRE = "genre";
    public static final String FILME_COLUMN_DIRECTOR = "director";
    public static final String FILME_COLUMN_YEAR = "year";
    public static final String FILME_COLUMN_POSTER = "poster";


    private static final String CREATE_TABLE_FILME = "CREATE TABLE IF NOT EXISTS " + FILME_TABLE_NAME + " ( "
            + FILME_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FILME_COLUMN_TITLE + " TEXT,"
            + FILME_COLUMN_GENRE + " TEXT,"
            + FILME_COLUMN_DIRECTOR + " TEXT,"
            + FILME_COLUMN_YEAR + " TEXT"
            + FILME_COLUMN_POSTER + " TEXT)";

    public DataBaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try {
            Log.i("CRIE", CREATE_TABLE_FILME);
            db.execSQL(CREATE_TABLE_FILME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        try {
            db.execSQL("DROP TABLE IF EXISTS " + FILME_TABLE_NAME);
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertFilme(String title, String genre, String director, String year) {
        try {
            if (exist(title)) {
                return false;
            } else {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("title", title);
                contentValues.put("genre", genre);
                contentValues.put("director", director);
                contentValues.put("year", year);
                db.insert(FILME_TABLE_NAME, null, contentValues);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Filme getFilme() {
        Filme filme = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from " + FILME_TABLE_NAME, null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                //f = new ClienteObjectPorIdDAO().pegaClientePorId(Long.valueOf(res.getString(res.getColumnIndex(SESSAO_COLUMN_ID_CLIENTE))));
                // Log.i("CLIENTE", cliente.getNome());
                res.moveToNext();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filme;
    }


    public Cursor getFilmeId(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + FILME_TABLE_NAME + " where id=" + id + "", null);
        return res;
    }

    public Boolean exist(String title) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Log.i("EXISTE?", "select * from " + FILME_TABLE_NAME + "  where title='" + title + "'");
            Cursor res = db.rawQuery("select * from " + FILME_TABLE_NAME + "  where title='" + title + "'", null);
            if (res.getCount() == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, FILME_TABLE_NAME);
        return numRows;
    }

    public boolean updateFilme(Integer id, String title, String genre, String director, String year, String poster) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("genre", genre);
        contentValues.put("director", director);
        contentValues.put("year", year);
        //contentValues.put("poster", poster);
        db.update(FILME_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteFilmeById(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(FILME_TABLE_NAME,
                "idProduto = ? ",
                new String[]{Long.toString(id)});
    }


    public ArrayList<Filme> getAllFilme() {
        Log.i("Numero de Linhas", String.valueOf(numberOfRows()));

        ArrayList<Filme> listFilmes = new ArrayList<Filme>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + FILME_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Filme f = new Filme();

            f.setTitle(res.getString(res.getColumnIndex(FILME_COLUMN_TITLE)));
            f.setGenre(res.getString(res.getColumnIndex(FILME_COLUMN_GENRE)));
            f.setDirector(res.getString(res.getColumnIndex(FILME_COLUMN_DIRECTOR)));
            f.setYear(res.getString(res.getColumnIndex(FILME_COLUMN_YEAR)));


            listFilmes.add(f);
            res.moveToNext();
        }
        return listFilmes;
    }

}
