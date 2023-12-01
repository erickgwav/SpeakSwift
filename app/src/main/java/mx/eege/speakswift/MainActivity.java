package mx.eege.speakswift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

// Actividad principal que muestra la pantalla de inicio
public class MainActivity extends AppCompatActivity {
    // Manejadores de sensores para la detección de agitación del dispositivo
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inicialización de elementos de la interfaz de usuario
        ImageView libroImg = findViewById(R.id.libroImg);
        ImageView oidoImg = findViewById(R.id.oidoImg);
        ImageView musicaImg = findViewById(R.id.musicaImg);
        ImageView chatImg = findViewById(R.id.chatImg);
        TextView expTxt = findViewById(R.id.exp);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.inicio);
        // Obtención de la experiencia actual del usuario desde la base de datos
        ExpDB expDB = new ExpDB(getApplicationContext());
        int exp = expDB.obtenerValorActual("1");
        expTxt.setText("EXP: " + exp);
        // Configuración del listener de la barra de navegación inferior
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.cuentos) {
                    startNewActivity(CuentosListaActivity.class);
                    return true;
                } else if (item.getItemId() == R.id.chat) {
                    showToast("Chat seleccionado");
                    return true;
                } else if (item.getItemId() == R.id.inicio) {
                    startNewActivity(MainActivity.class);
                    return true;
                } else if (item.getItemId() == R.id.musica) {
                    showToast("Música seleccionada");
                    return true;
                } else if (item.getItemId() == R.id.dictados) {
                    startNewActivity(DictadosListaActivity.class);
                    return true;
                }
                return false;
            }
        });
        // Inicialización del sensor de acelerómetro
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Configuración de listeners para imágenes de la pantalla de inicio
        libroImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(CuentosListaActivity.class);
            }
        });
        oidoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(DictadosListaActivity.class);
            }
        });
        musicaImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Música seleccionada");
            }
        });
        chatImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Chat seleccionado");
            }
        });
    }

    // Método para mostrar un mensaje de tostada
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Método para iniciar una nueva actividad
    private void startNewActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }

    // Método llamado cuando la actividad se reanuda
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Método llamado cuando la actividad se pausa
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accelerometerListener);
    }

    // Listener para el sensor de acelerómetro
    private SensorEventListener accelerometerListener = new SensorEventListener() {
        private static final float ALPHA = 0.8f;
        private static final float SHAKE_THRESHOLD = 10.0f;
        private long lastShakeTime = 0;
        private float[] gravity = new float[3];
        @Override
        public void onSensorChanged(SensorEvent event) {
            // Actualiza los valores de la gravedad para filtrar el ruido del sensor de acelerómetro
            gravity[0] = ALPHA * gravity[0] + (1 - ALPHA) * event.values[0];
            gravity[1] = ALPHA * gravity[1] + (1 - ALPHA) * event.values[1];
            gravity[2] = ALPHA * gravity[2] + (1 - ALPHA) * event.values[2];
            // Calcula la aceleración en cada eje
            float x = event.values[0] - gravity[0];
            float y = event.values[1] - gravity[1];
            float z = event.values[2] - gravity[2];
            // Calcula la magnitud de la aceleración total
            double acceleration = Math.sqrt(x * x + y * y + z * z);
            // Verifica si la magnitud de la aceleración supera el umbral de agitación
            if (acceleration > SHAKE_THRESHOLD) {
                // Obtiene el tiempo actual
                long currentTime = System.currentTimeMillis();
                // Verifica si ha pasado suficiente tiempo desde la última agitación
                if ((currentTime - lastShakeTime) > 300000) { // 300,000 milisegundos = 5 minutos
                    // Actualiza el tiempo de la última agitación
                    lastShakeTime = currentTime;
                    // Muestra el diálogo de recompensa
                    showRewardDialog();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Método vacío; no se utiliza en este contexto
        }

        // Método para mostrar el diálogo de recompensa
        private void showRewardDialog() {
            // Creación del constructor del diálogo
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
            // Inflado del diseño personalizado para el diálogo
            View view = LayoutInflater.from(MainActivity.this).inflate(
                    R.layout.layout_reward_dialog,
                    (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
            );
            builder.setView(view);
            // Configuración del título y el mensaje del diálogo
            ((TextView) view.findViewById(R.id.textTitle)).setText("¡Felicidades!");
            ((TextView) view.findViewById(R.id.textMessage))
                    .setText("Has ganado 25 XP. Vuelve a agitar el dispositivo en la pantalla de inicio " +
                            "dentro de unos minutos para ganar más XP");
            // Configuración del botón de acción y su listener
            ((Button) view.findViewById(R.id.buttonAction)).setText("Entendido");
            final AlertDialog alertDialog = builder.create();
            view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Actualización de la experiencia del usuario en la base de datos
                    ExpDB expDB = new ExpDB(getApplicationContext());
                    expDB.aumentarExp(25);

                    // Cierre del diálogo
                    alertDialog.dismiss();
                }
            });
            // Configuración del fondo del diálogo
            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            // Mostrar el diálogo
            alertDialog.show();
        }
    };
}