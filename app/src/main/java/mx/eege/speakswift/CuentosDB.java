package mx.eege.speakswift;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CuentosDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "speak_swift";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_CUENTOS = "cuentos";

    private static final String CREATE_TABLE_CUENTOS =
            "CREATE TABLE " + TABLE_CUENTOS + " (id INTEGER PRIMARY KEY, titulo TEXT, contenido TEXT, imagen TEXT," +
                    "pregunta1 TEXT, answ1_1 TEXT, answ1_2 TEXT, answ1_3 TEXT, answ1 TEXT, pregunta2 TEXT, " +
                    "answ2_1 TEXT, answ2_2 TEXT, answ2_3 TEXT, answ2 TEXT, pregunta3 TEXT, answ3_1 TEXT, " +
                    "answ3_2 TEXT, answ3_3 TEXT, answ3 TEXT, completado INTEGER DEFAULT 0)";

    private static final String DROP_TABLE_CUENTOS =
            "DROP TABLE " + TABLE_CUENTOS + ";";
    public CuentosDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CUENTOS);
        insertarCuentos(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUENTOS);
        onCreate(db);
    }

    private void insertarCuentos(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("titulo", "Snow White");
        values.put("contenido", "cuento1.txt");
        values.put("imagen", "cuento1");
        values.put("pregunta1", "¿Por qué Fluffy no puede encontrar a Lisa cuando se pierde?");
        values.put("answ1_1", "a) Lisa no está buscando lo suficientemente duro.");
        values.put("answ1_2", "b) Fluffy se esconde intencionalmente.");
        values.put("answ1_3", "c) Lisa ha dejado de buscar a Fluffy.");
        values.put("answ1", "answ1_3");
        values.put("pregunta2", "¿Qué es lo que Fluffy quiere a las 8 de la noche?");
        values.put("answ2_1", "a) Su cena.");
        values.put("answ2_2", "b) Un paseo.");
        values.put("answ2_3", "c) Su baño.");
        values.put("answ2", "answ2_1");
        values.put("pregunta3", "¿Cómo reacciona el perro gris grande cuando Fluffy le pide comida, snacks y un baño?");
        values.put("answ3_1", "a) Se enoja y le dice a Fluffy que se vaya.");
        values.put("answ3_2", "b) Acepta y lleva a Fluffy a su spa.");
        values.put("answ3_3", "c) Ignora a Fluffy y continúa masticando.");
        values.put("answ3", "answ3_2");
        values.put("completado", 0);
        db.insert(TABLE_CUENTOS, null, values);
        values.clear();

        values.put("id", 2);
        values.put("titulo", "The Ugly Duckling");
        values.put("contenido", "cuento2.txt");
        values.put("imagen", "cuento2");
        values.put("pregunta1", "¿Por qué la madre pata se preocupaba por uno de sus patitos después de que todos los huevos se hubieran abierto?");
        values.put("answ1_1", "a) Porque el patito más grande se veía diferente.");
        values.put("answ1_2", "b) Porque el patito más grande no quería salir del huevo.");
        values.put("answ1_3", "c) Porque todos los patitos eran del mismo tamaño.");
        values.put("answ1", "answ1_1");
        values.put("pregunta2", "¿Por qué los hermanos y hermanas del patito feo lo odiaban y se burlaban de él?");
        values.put("answ2_1", "a) Porque siempre estaba solo.");
        values.put("answ2_2", "b) Porque tenía plumaje gris en lugar de amarillo.");
        values.put("answ2_3", "c) Porque era el más pequeño de la camada.");
        values.put("answ2", "answ2_2");
        values.put("pregunta3", "¿Qué descubre el patito feo sobre sí mismo cuando ve su reflejo en el agua?");
        values.put("answ3_1", "a) Que sigue siendo feo.");
        values.put("answ3_2", "b) Que se ha convertido en un pato adulto.");
        values.put("answ3_3", "c) Que en realidad es un cisne hermoso.");
        values.put("answ3", "answ3_3");
        values.put("completado", 0);
        db.insert(TABLE_CUENTOS, null, values);
        values.clear();

        values.put("id", 3);
        values.put("titulo", "Pinocchio");
        values.put("contenido", "cuento3.txt");
        values.put("imagen", "cuento3");
        values.put("pregunta1", "¿Por qué Geppetto vendió su único abrigo?");
        values.put("answ1_1", "a) Para comprar un libro de ortografía.");
        values.put("answ1_2", "b) Para comprar madera para hacer más marionetas.");
        values.put("answ1_3", "c) Para enviar a Pinocchio a la escuela.");
        values.put("answ1", "answ1_1");
        values.put("pregunta2", "¿Qué lleva a Pinocchio a ser encerrado por el malvado maestro de marionetas?");
        values.put("answ2_1", "a) Porque quiere aprender a hacer marionetas.");
        values.put("answ2_2", "b) Porque acepta unirse a su espectáculo de marionetas.");
        values.put("answ2_3", "c) Porque quiere robarle las marionetas al maestro..");
        values.put("answ2", "answ2_2");
        values.put("pregunta3", "¿Cómo es liberado Pinocchio del malvado maestro de marionetas?");
        values.put("answ3_1", "a) Geppetto lo rescata.");
        values.put("answ3_2", "b) La hada azul aparece y lo libera con un hechizo.");
        values.put("answ3_3", "c) Escapa por sí mismo.");
        values.put("answ3", "answ3_2");
        values.put("completado", 0);
        db.insert(TABLE_CUENTOS, null, values);
        values.clear();
    }

    public List<Cuento> getAllCuentos() {
        List<Cuento> listaCuentos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta SQL para obtener todos los cuentos
        String selectQuery = "SELECT * FROM " + TABLE_CUENTOS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Iterar a través de las filas y agregar cuentos a la lista
        if (cursor.moveToFirst()) {
            do {
                Cuento cuento = new Cuento();
                cuento.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                cuento.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
                cuento.setContenido(cursor.getString(cursor.getColumnIndexOrThrow("contenido")));
                cuento.setImagen(cursor.getString(cursor.getColumnIndexOrThrow("imagen")));
                cuento.setPregunta1(cursor.getString(cursor.getColumnIndexOrThrow("pregunta1")));
                cuento.setAnsw1_1(cursor.getString(cursor.getColumnIndexOrThrow("answ1_1")));
                cuento.setAnsw1_2(cursor.getString(cursor.getColumnIndexOrThrow("answ1_2")));
                cuento.setAnsw1_3(cursor.getString(cursor.getColumnIndexOrThrow("answ1_3")));
                cuento.setAnsw1(cursor.getString(cursor.getColumnIndexOrThrow("answ1")));
                cuento.setPregunta2(cursor.getString(cursor.getColumnIndexOrThrow("pregunta2")));
                cuento.setAnsw2_1(cursor.getString(cursor.getColumnIndexOrThrow("answ2_1")));
                cuento.setAnsw2_2(cursor.getString(cursor.getColumnIndexOrThrow("answ2_2")));
                cuento.setAnsw2_3(cursor.getString(cursor.getColumnIndexOrThrow("answ2_3")));
                cuento.setAnsw2(cursor.getString(cursor.getColumnIndexOrThrow("answ2")));
                cuento.setPregunta3(cursor.getString(cursor.getColumnIndexOrThrow("pregunta3")));
                cuento.setAnsw3_1(cursor.getString(cursor.getColumnIndexOrThrow("answ3_1")));
                cuento.setAnsw3_2(cursor.getString(cursor.getColumnIndexOrThrow("answ3_2")));
                cuento.setAnsw3_3(cursor.getString(cursor.getColumnIndexOrThrow("answ3_3")));
                cuento.setAnsw3(cursor.getString(cursor.getColumnIndexOrThrow("answ3")));
                cuento.setCompletado(cursor.getInt(cursor.getColumnIndexOrThrow("completado")));
                listaCuentos.add(cuento);
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la conexión a la base de datos
        cursor.close();
        db.close();

        return listaCuentos;
    }

    public void actualizarCompletado(int cuentoId, int valor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("completado", valor);
        db.update(TABLE_CUENTOS, values, "id = ?", new String[]{String.valueOf(cuentoId)});
        db.close();
    }
}
