package mx.eege.speakswift;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la base de datos SQLite para la tabla de dictados.
 */
public class DictadosDB extends SQLiteOpenHelper {
    // Definición de constantes para el nombre de la base de datos, versión y nombre de la tabla
    private static final String DATABASE_NAME = "speak_swiftdi";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_DICTADOS = "dictados";

    // Definición de sentencias SQL para crear y eliminar la tabla de dictados
    private static final String CREATE_TABLE_DICTADOS =
            "CREATE TABLE " + TABLE_DICTADOS + " (id INTEGER PRIMARY KEY, titulo TEXT, archivo TEXT, palabras TEXT, " +
                    "completado INTEGER DEFAULT 0)";

    private static final String DROP_TABLE_DICTADOS =
            "DROP TABLE " + TABLE_DICTADOS + ";";

    // Constructor de la clase
    public DictadosDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método llamado al crear la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DICTADOS);
        insertarDictados(db);
    }

    // Método llamado al actualizar la versión de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTADOS);
        onCreate(db);
    }

    // Método privado para insertar datos iniciales en la tabla de dictados
    private void insertarDictados(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("titulo", "Adjectives");
        values.put("archivo", "adjectives");
        values.put("palabras", "pink, worry, mountain!, delicious!, brown, tall, fast!, friend, dress, airplane!");
        db.insert(TABLE_DICTADOS, null, values);
        values.clear();

        values.put("id", 2);
        values.put("titulo", "Cooking");
        values.put("archivo", "cooking");
        values.put("palabras", "oven, kitchen, batch!, viewer!, ordering, recipe, ingredient, grandma!, wait, dirty, television!, sell!");
        db.insert(TABLE_DICTADOS, null, values);
        values.clear();

        values.put("id", 3);
        values.put("titulo", "Holidays");
        values.put("archivo", "holidays");
        values.put("palabras", "celebration!, holiday, bedtime, adventure!, family, dinner, picnic, harmony!, laughter!, coworker, playground, rainbow!");
        db.insert(TABLE_DICTADOS, null, values);
        values.clear();

        values.put("id", 4);
        values.put("titulo", "Internet");
        values.put("archivo", "internet");
        values.put("palabras", "rainbow!, computer, security!, firewall, data, encryption!, Internet, viruses, adventure!, antivirus, friends, harmony!");
        db.insert(TABLE_DICTADOS, null, values);
        values.clear();

        values.put("id", 5);
        values.put("titulo", "Poem");
        values.put("archivo", "poem");
        values.put("palabras", "melody!, sun, nostalgia!, window, whispers!, breath, born, enchantment!, night, house, serenity!, remember");
        db.insert(TABLE_DICTADOS, null, values);
        values.clear();

        values.put("id", 6);
        values.put("titulo", "Quebec");
        values.put("archivo", "quebec");
        values.put("palabras", "twilight!, province, whimsy!, city, Quebec, wedding, harmony!, Montreal, Bob, serendipity!, summer, discovery!");
        db.insert(TABLE_DICTADOS, null, values);
        values.clear();

        values.put("id", 7);
        values.put("titulo", "Reservation");
        values.put("archivo", "reservation");
        values.put("palabras", "whispers!, March, adventure!, Sunnyside, discovery!, room, serenity!, kitchenette, reserve, harmony!, suite, Inn");
        db.insert(TABLE_DICTADOS, null, values);
        values.clear();

        values.put("id", 8);
        values.put("titulo", "School supplies");
        values.put("archivo", "school_supplies");
        values.put("palabras", "suite!, ruler, grade, adventure!, calculator, school, melody!, supplies, whispers!, March!, pencils, eraser");
        db.insert(TABLE_DICTADOS, null, values);
        values.clear();

        values.put("id", 9);
        values.put("titulo", "Travelling");
        values.put("archivo", "travelling");
        values.put("palabras", "adventure!, crammed, bumped, terrible, whispers!, flight, province!, melody!, volunteers, hours!, messy, overbooked");
        db.insert(TABLE_DICTADOS, null, values);
        values.clear();

        values.put("id", 10);
        values.put("titulo", "Working hours");
        values.put("archivo", "working_hours");
        values.put("palabras", "province!, France, whispers!, hours, melody!, work, serendipity!, Japan, flight!, overtime, families, relax");
        db.insert(TABLE_DICTADOS, null, values);
        values.clear();
    }

    // Método para obtener todos los dictados de la tabla
    public List<Dictado> getAllDictados() {
        List<Dictado> listaDictados = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_DICTADOS;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Dictado dictado = new Dictado();
                dictado.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                dictado.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
                dictado.setArchivo(cursor.getString(cursor.getColumnIndexOrThrow("archivo")));
                dictado.setPalabras(cursor.getString(cursor.getColumnIndexOrThrow("palabras")));
                dictado.setCompletado(cursor.getInt(cursor.getColumnIndexOrThrow("completado")));
                listaDictados.add(dictado);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return listaDictados;
    }

    // Método para actualizar el estado "completado" de un dictado
    public void actualizarCompletado(int dictadoId, int valor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("completado", valor);
        db.update(TABLE_DICTADOS, values, "id = ?", new String[]{String.valueOf(dictadoId)});
    }
}
