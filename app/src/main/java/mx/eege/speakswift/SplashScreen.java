package mx.eege.speakswift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

// Definición de la clase SplashScreen que extiende AppCompatActivity
public class SplashScreen extends AppCompatActivity {

    // Método llamado cuando se crea la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el diseño de la actividad desde el archivo XML
        setContentView(R.layout.activity_splash_screen);
        // Crea un nuevo Handler para programar la transición a la actividad principal después de un
        // retraso de 2000 milisegundos (2 segundos)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Crea un intent para iniciar la actividad principal (MainActivity)
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                // Inicia la nueva actividad
                startActivity(intent);
                // Finaliza la actividad actual para que no se pueda regresar a la pantalla de bienvenida
                finish();
            }
        }, 2000); // El tiempo de retraso es de 2000 milisegundos (2 segundos)
    }
}