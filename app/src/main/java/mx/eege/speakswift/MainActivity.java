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

public class MainActivity extends AppCompatActivity implements MainView.OnRoundRectClickListener{

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainView mainView = findViewById(R.id.mainView);
        mainView.setOnRoundRectClickListener(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.inicio);
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
                    showToast("Dictados seleccionados");
                    return true;
                }
                return false;
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startNewActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
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
                        showRewardDialog();
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        private void showRewardDialog(){
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
                    .setText("Has ganado 5 XP. Vuelve a agitar el dispositivo en la pantalla de inicio " +
                            "dentro de unos minutos para ganar más XP");
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

    @Override
    public void onRect2Click() {
        showToast("Dictados seleccionados");
    }

    @Override
    public void onRect3Click() {
        startNewActivity(CuentosListaActivity.class);
    }

    @Override
    public void onRect4Click() {
        showToast("Música seleccionada");
    }

    @Override
    public void onRect5Click() {
        showToast("Chat seleccionado");
    }

}