package mx.eege.speakswift;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase que representa un objeto Dictado y implementa la interfaz Parcelable para permitir su paso
 * entre componentes de la aplicación.
 */
public class Dictado implements Parcelable {
    // Atributos de la clase
    private int id;
    private String titulo;
    private String archivo;
    private String palabras;
    private int completado = 0;

    // Métodos getter y setter para acceder a los atributos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getPalabras() {
        return palabras;
    }

    public void setPalabras(String palabras) {
        this.palabras = palabras;
    }

    public int getCompletado() {
        return completado;
    }

    public void setCompletado(int completado) {
        this.completado = completado;
    }

    // Constructor vacío
    public Dictado() {}

    // Constructor que recibe un objeto Parcel para construir el objeto Dictado
    protected Dictado(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        archivo = in.readString();
        palabras = in.readString();
        completado = in.readInt();
    }

    // Interfaz Parcelable: Creador estático y método para crear un array de objetos Dictado
    public static final Creator<Dictado> CREATOR = new Creator<Dictado>() {
        @Override
        public Dictado createFromParcel(Parcel in) {
            return new Dictado(in);
        }

        @Override
        public Dictado[] newArray(int size) {
            return new Dictado[size];
        }
    };

    // Interfaz Parcelable: Métodos para describir el contenido del objeto y escribir/leer los atributos en/desde el Parcel
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(archivo);
        dest.writeString(palabras);
        dest.writeInt(completado);
    }
}

