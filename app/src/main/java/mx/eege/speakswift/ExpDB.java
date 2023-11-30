package mx.eege.speakswift;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ExpDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "speak_swifte";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_PROGRESO =
            "CREATE TABLE progreso (id INTEGER PRIMARY KEY, exp INTEGER DEFAULT 0)";

    private static final String DROP_TABLE_PROGRESO =
            "DROP TABLE progreso;";
    public ExpDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROGRESO);
        insertarProgreso(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS progreso");
        onCreate(db);
    }

    private void insertarProgreso(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("exp", 0);
        db.insert("progreso", null, values);
    }

    public void aumentarExp(int valorAumento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int valorActual = obtenerValorActual("1");

        int nuevoValor = valorActual + valorAumento;

        values.put("exp", nuevoValor);
        db.update("progreso", values, "id = ?", new String[]{"1"});

        db.close();
    }

    public int obtenerValorActual(String idRegistro) {
        SQLiteDatabase db = this.getReadableDatabase();
        int valorActual = 0;

        Cursor cursor = db.query("progreso",
                new String[]{"exp"},
                "id = ?",
                new String[]{idRegistro},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                valorActual = cursor.getInt(cursor.getColumnIndexOrThrow("exp"));
            }
            cursor.close();
        }

        return valorActual;
    }
}
