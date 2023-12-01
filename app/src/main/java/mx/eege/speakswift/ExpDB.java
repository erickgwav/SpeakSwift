package mx.eege.speakswift;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

// Clase que gestiona la base de datos SQLite para el progreso de la aplicación
public class ExpDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "speak_swifte";
    private static final int DATABASE_VERSION = 1;

    // Consulta SQL para crear la tabla de progreso
    private static final String CREATE_TABLE_PROGRESO =
            "CREATE TABLE progreso (id INTEGER PRIMARY KEY, exp INTEGER DEFAULT 0)";

    // Consulta SQL para eliminar la tabla de progreso
    private static final String DROP_TABLE_PROGRESO =
            "DROP TABLE progreso;";

    // Constructor de la clase que recibe el contexto
    public ExpDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método llamado cuando se crea la base de datos por primera vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROGRESO);
        insertarProgreso(db);
    }

    // Método llamado cuando se actualiza la versión de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS progreso");
        onCreate(db);
    }

    // Método privado para insertar un registro de progreso inicial en la tabla
    private void insertarProgreso(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("exp", 0);
        db.insert("progreso", null, values);
    }

    // Método para aumentar la experiencia en la tabla de progreso
    public void aumentarExp(int valorAumento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Obtiene el valor actual de experiencia
        int valorActual = obtenerValorActual("1");
        // Calcula el nuevo valor de experiencia
        int nuevoValor = valorActual + valorAumento;
        // Actualiza la experiencia en la base de datos
        values.put("exp", nuevoValor);
        db.update("progreso", values, "id = ?", new String[]{"1"});
        // Cierra la conexión a la base de datos
        db.close();
    }

    // Método para obtener el valor actual de experiencia
    public int obtenerValorActual(String idRegistro) {
        SQLiteDatabase db = this.getReadableDatabase();
        int valorActual = 0;
        // Consulta para obtener la experiencia del registro con el ID proporcionado
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
        // Cierra la conexión a la base de datos
        db.close();
        return valorActual;
    }
}

