package mx.eege.speakswift;

import android.os.Parcel;
import android.os.Parcelable;

public class Cuento implements Parcelable {
    private int id;
    private String titulo;
    private String contenido;
    private String imagen;
    private String pregunta1;
    private String answ1_1;
    private String answ1_2;
    private String answ1_3;
    private String answ1;
    private String pregunta2;
    private String answ2_1;
    private String answ2_2;
    private String answ2_3;
    private String answ2;
    private String pregunta3;
    private String answ3_1;
    private String answ3_2;
    private String answ3_3;
    private String answ3;
    private int completado;


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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public String getAnsw1_1() {
        return answ1_1;
    }

    public void setAnsw1_1(String answ1_1) {
        this.answ1_1 = answ1_1;
    }

    public String getAnsw1_2() {
        return answ1_2;
    }

    public void setAnsw1_2(String answ1_2) {
        this.answ1_2 = answ1_2;
    }

    public String getAnsw1_3() {
        return answ1_3;
    }

    public void setAnsw1_3(String answ1_3) {
        this.answ1_3 = answ1_3;
    }

    public String getAnsw1() {
        return answ1;
    }

    public void setAnsw1(String answ1) {
        this.answ1 = answ1;
    }

    public String getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(String pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public String getAnsw2_1() {
        return answ2_1;
    }

    public void setAnsw2_1(String answ2_1) {
        this.answ2_1 = answ2_1;
    }

    public String getAnsw2_2() {
        return answ2_2;
    }

    public void setAnsw2_2(String answ2_2) {
        this.answ2_2 = answ2_2;
    }

    public String getAnsw2_3() {
        return answ2_3;
    }

    public void setAnsw2_3(String answ2_3) {
        this.answ2_3 = answ2_3;
    }

    public String getAnsw2() {
        return answ2;
    }

    public void setAnsw2(String answ2) {
        this.answ2 = answ2;
    }

    public String getPregunta3() {
        return pregunta3;
    }

    public void setPregunta3(String pregunta3) {
        this.pregunta3 = pregunta3;
    }

    public String getAnsw3_1() {
        return answ3_1;
    }

    public void setAnsw3_1(String answ3_1) {
        this.answ3_1 = answ3_1;
    }

    public String getAnsw3_2() {
        return answ3_2;
    }

    public void setAnsw3_2(String answ3_2) {
        this.answ3_2 = answ3_2;
    }

    public String getAnsw3_3() {
        return answ3_3;
    }

    public void setAnsw3_3(String answ3_3) {
        this.answ3_3 = answ3_3;
    }

    public String getAnsw3() {
        return answ3;
    }

    public void setAnsw3(String answ3) {
        this.answ3 = answ3;
    }

    public int getCompletado() {
        return completado;
    }

    public void setCompletado(int completado) {
        this.completado = completado;
    }

    public Cuento() {
        return;
    }
    protected Cuento(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        contenido = in.readString();
        imagen = in.readString();
        pregunta1 = in.readString();
        answ1_1 = in.readString();
        answ1_2 = in.readString();
        answ1_3 = in.readString();
        answ1 = in.readString();
        pregunta2 = in.readString();
        answ2_1 = in.readString();
        answ2_2 = in.readString();
        answ2_3 = in.readString();
        answ2 = in.readString();
        pregunta3 = in.readString();
        answ3_1 = in.readString();
        answ3_2 = in.readString();
        answ3_3 = in.readString();
        answ3 = in.readString();
        completado = in.readInt();
    }

    public static final Creator<Cuento> CREATOR = new Creator<Cuento>() {
        @Override
        public Cuento createFromParcel(Parcel in) {
            return new Cuento(in);
        }

        @Override
        public Cuento[] newArray(int size) {
            return new Cuento[size];
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
        dest.writeString(contenido);
        dest.writeString(imagen);
        dest.writeString(pregunta1);
        dest.writeString(answ1_1);
        dest.writeString(answ1_2);
        dest.writeString(answ1_3);
        dest.writeString(answ1);
        dest.writeString(pregunta2);
        dest.writeString(answ2_1);
        dest.writeString(answ2_2);
        dest.writeString(answ2_3);
        dest.writeString(answ2);
        dest.writeString(pregunta3);
        dest.writeString(answ3_1);
        dest.writeString(answ3_2);
        dest.writeString(answ3_3);
        dest.writeString(answ3);
        dest.writeInt(completado);
    }

}
