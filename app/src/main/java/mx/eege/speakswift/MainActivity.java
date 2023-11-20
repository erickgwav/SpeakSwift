package mx.eege.speakswift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.inicio);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.cuentos) {
                    showToast("Cuentos seleccionados");
                    // Lógica para el elemento "Cuentos"
                    return true;
                } else if (item.getItemId() == R.id.chat) {
                    showToast("Chat seleccionado");
                    // Lógica para el elemento "Chat"
                    return true;
                } else if (item.getItemId() == R.id.inicio) {
                    showToast("Inicio seleccionado");
                    // Lógica para el elemento "Inicio"
                    return true;
                } else if (item.getItemId() == R.id.musica) {
                    showToast("Música seleccionada");
                    // Lógica para el elemento "Música"
                    return true;
                } else if (item.getItemId() == R.id.dictados) {
                    showToast("Dictados seleccionados");
                    // Lógica para el elemento "Dictados"
                    return true;
                }
                return false;
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    private void showToast(String message) {
        // Muestra un Toast con el mensaje proporcionado
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accelerometerListener);
    }

    private SensorEventListener accelerometerListener = new SensorEventListener() {
        private static final float ALPHA = 0.8f;
        private static final float SHAKE_THRESHOLD = 10.0f;
        private long lastShakeTime = 0;
        private float[] gravity = new float[3];

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                gravity[0] = ALPHA * gravity[0] + (1 - ALPHA) * event.values[0];
                gravity[1] = ALPHA * gravity[1] + (1 - ALPHA) * event.values[1];
                gravity[2] = ALPHA * gravity[2] + (1 - ALPHA) * event.values[2];

                float x = event.values[0] - gravity[0];
                float y = event.values[1] - gravity[1];
                float z = event.values[2] - gravity[2];

                double acceleration = Math.sqrt(x * x + y * y + z * z);

                if (acceleration > SHAKE_THRESHOLD) {
                    long currentTime = System.currentTimeMillis();
                    if ((currentTime - lastShakeTime) > 300000) {
                        lastShakeTime = currentTime;
                        showErrorDialog();
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        private void showErrorDialog(){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder
                            (MainActivity.this, R.style.AlertDialogTheme);
            View view = LayoutInflater.from(MainActivity.this).inflate(
                    R.layout.layout_reward_dialog,
                    (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
            );
            builder.setView(view);
            ((TextView) view.findViewById(R.id.textTitle))
                    .setText("¡Felicidades!");
            ((TextView) view.findViewById(R.id.textMessage))
                    .setText("Has ganado 5 XP. Vuelve a agitar el dispositivo en unos minutos para ganar más XP");
            ((Button) view.findViewById(R.id.buttonAction))
                    .setText("Entendido");
            final AlertDialog alertDialog = builder.create();
            view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            if (alertDialog.getWindow() != null){
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();
        }
    };

}