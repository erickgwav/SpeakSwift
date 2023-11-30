package mx.eege.speakswift;

import android.os.Parcel;
import android.os.Parcelable;

public class Dictado implements Parcelable {
    private int id;
    private String titulo;
    private String archivo;
    private String palabras;
    private int completado = 0;

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

    public Dictado() {
        return;
    }

    protected Dictado(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        archivo = in.readString();
        palabras = in.readString();
        completado = in.readInt();
    }

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

    public int getCompletado() {
        return completado;
    }

    public void setCompletado(int completado) {
        this.completado = completado;
    }
}
