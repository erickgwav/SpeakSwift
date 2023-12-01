package mx.eege.speakswift;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * La clase DictadoActivity representa la pantalla donde se reproduce un dictado
 * y se proporciona una interfaz para que el usuario marque las palabras correctas e incorrectas.
 */
public class DictadoActivity extends AppCompatActivity {
    // Declaración de variables de la interfaz de usuario y lógica del dictado
    TextView tituloTxt;
    TextView playerPosition, playerDuration;
    SeekBar seekBar;
    ImageView btRew, btPlay, btPause, btFf;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;
    private int errores = 0;
    private int aciertos = 0;
    private int exp = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictado);

        // Inicialización de elementos de la interfaz de usuario
        tituloTxt = findViewById(R.id.titulo);
        playerPosition = findViewById(R.id.player_position);
        playerDuration = findViewById(R.id.player_duration);
        seekBar = findViewById(R.id.seek_bar);
        btRew = findViewById(R.id.bt_rew);
        btPlay = findViewById(R.id.bt_play);
        btPause = findViewById(R.id.bt_pause);
        btFf = findViewById(R.id.bt_ff);

        // Obtención del objeto Dictado pasado desde la actividad anterior
        Dictado dictado = getIntent().getParcelableExtra("dictado");
        int id = dictado.getId();
        String titulo = dictado.getTitulo();
        String archivo = dictado.getArchivo();
        String palabras = dictado.getPalabras();

        // Inicialización de la cuadrícula para las palabras del dictado
        GridLayout gridLayout = findViewById(R.id.contenedor);

        // Separación de palabras correctas e incorrectas
        List<String> palabrasArray = Arrays.asList(palabras.split(", "));
        ArrayList<String> palabrasIncorrectas = new ArrayList<>();
        ArrayList<String> palabrasCorrectas = new ArrayList<>();

        // Creación de CheckBoxes dinámicamente para cada palabra en la cuadrícula
        for (String palabra : palabrasArray) {
            if (palabra.endsWith("!")) {
                palabrasIncorrectas.add(palabra.substring(0, palabra.length() - 1));
            } else {
                palabrasCorrectas.add(palabra);
            }
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(palabra.endsWith("!") ? palabra.substring(0, palabra.length() - 1) : palabra);
            // Configuración de apariencia y estilo de los CheckBox
            checkBox.setBackgroundResource(R.drawable.selector_checkbox);
            checkBox.setButtonDrawable(android.R.color.transparent);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.montserrat_semibold);
            checkBox.setTypeface(typeface);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setPadding(0, 20, 0, 20);
            ColorStateList textColor = getResources().getColorStateList(R.color.selector_text_color_checkbox);
            checkBox.setTextColor(textColor);
            checkBox.setChecked(false);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(4, 8, 4, 8);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            checkBox.setLayoutParams(params);
            gridLayout.addView(checkBox);
        }

        // Configuración del título del dictado
        tituloTxt.setText(titulo);

        // Obtención del recurso de audio asociado al dictado
        int resourceId = getResources().getIdentifier(archivo, "raw", getPackageName());

        // Inicialización del reproductor de audio
        mediaPlayer = MediaPlayer.create(this, resourceId);

        // Configuración de la lógica para la actualización de la barra de progreso
        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
                handler.postDelayed(this, 500);
            }
        };

        // Configuración de la duración del dictado en la interfaz de usuario
        int duration = mediaPlayer.getDuration();
        String sDuration = convertFormat(duration);
        playerDuration.setText(sDuration);

        // Configuración de los botones de reproducción y su funcionalidad
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btPlay.setVisibility(View.GONE);
                btPause.setVisibility(View.VISIBLE);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                handler.postDelayed(runnable, 0);
            }
        });

        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btPause.setVisibility(View.GONE);
                btPlay.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                handler.removeCallbacks(runnable);
            }
        });

        // Configuración de los botones de avance y retroceso
        btFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                if (mediaPlayer.isPlaying() && duration != currentPosition) {
                    currentPosition += 5000;
                    playerPosition.setText(convertFormat(currentPosition));
                    mediaPlayer.seekTo(currentPosition);
                }
            }
        });

        btRew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                if (mediaPlayer.isPlaying() && currentPosition > 5000) {
                    currentPosition -= 5000;
                    playerPosition.setText(convertFormat(currentPosition));
                    mediaPlayer.seekTo(currentPosition);
                }
            }
        });

        // Configuración del cambio de posición en la barra de progreso
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
                playerPosition.setText(convertFormat(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Configuración de la acción al completar la reproducción del audio
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btPause.setVisibility(View.GONE);
                btPlay.setVisibility(View.VISIBLE);
                mediaPlayer.seekTo(0);
            }
        });

        // Configuración del menú de navegación inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.dictados);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Acciones correspondientes a las opciones del menú de navegación
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

        // Configuración del botón de envío de respuestas
        Button submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicialización de variables para evaluar respuestas
                errores = 0;
                aciertos = 0;
                exp = 0;
                // Evaluación de palabras correctas seleccionadas por el usuario
                for (String palabra : palabrasCorrectas) {
                    CheckBox checkBox = findCheckBoxByWord(gridLayout, palabra);
                    if (checkBox != null && checkBox.isChecked()) {
                        aciertos++;
                        exp += 15;
                    }
                }
                // Evaluación de palabras incorrectas seleccionadas por el usuario
                for (String palabraIncorrecta : palabrasIncorrectas) {
                    CheckBox checkBox = findCheckBoxByWord(gridLayout, palabraIncorrecta);
                    if (checkBox != null && checkBox.isChecked()) {
                        errores++;
                        exp -= 10;
                    }
                }
                // Manejo de resultados y actualización de la base de datos
                if(aciertos == 7) {
                    if(errores == 0) {
                        DictadosDB dictadosDB = new DictadosDB(getApplicationContext());
                        dictadosDB.actualizarCompletado(dictado.getId(), 1);
                    }
                    showResultsDialog(aciertos, errores, exp);
                } else {
                    showToast("¡Te faltan palabras correctas!");
                }
            }
        });
    }

    // Método que busca un CheckBox en un GridLayout según el texto de la palabra.
    private CheckBox findCheckBoxByWord(GridLayout gridLayout, String palabra) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) child;
                if (checkBox.getText().toString().equals(palabra)) {
                    return checkBox;
                }
            }
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberación de recursos al destruir la actividad
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Método que convierte la duración dada en milisegundos a un formato de tiempo HH:mm.
    private String convertFormat(int duration) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    // Método que muestra un mensaje Toast con el texto proporcionado.
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Método que inicia una nueva actividad según la clase proporcionada.
    private void startNewActivity(Class<?> cls) {
        Intent intent = new Intent(DictadoActivity.this, cls);
        startActivity(intent);
    }

    // Método que muestra un cuadro de diálogo con los resultados obtenidos por el usuario.
    public void showResultsDialog(int aciertos, int errores, int exp){
        AlertDialog.Builder builder =
                new AlertDialog.Builder
                        (DictadoActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(DictadoActivity.this).inflate(
                R.layout.layout_reward_dialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        // Configuración del contenido del cuadro de diálogo según los resultados
        if(errores == 0) {
            ((TextView) view.findViewById(R.id.textTitle))
                    .setText("¡Felicidades!");
            ((TextView) view.findViewById(R.id.textMessage))
                    .setText("Has completado satisfactoriamente esta actividad");
        } else if(errores > 0 && errores <= 2 ){
            ((TextView) view.findViewById(R.id.textTitle))
                    .setText("¡Casi lo tienes!");
            ((TextView) view.findViewById(R.id.textMessage))
                    .setText("Tuviste " + errores + " errores. Vuelve a intentarlo.");
        }
        else{
            ((TextView) view.findViewById(R.id.textTitle))
                    .setText("¡Ups!");
            ((TextView) view.findViewById(R.id.textMessage))
                    .setText("Tuviste " + errores + " errores. Vuelve a intentarlo.");
        }
        // Configuración del botón de acción en el cuadro de diálogo
        ((Button) view.findViewById(R.id.buttonAction))
                .setText("Obtener +" + exp + " EXP");
        // Configuración de la acción al hacer clic en el botón del cuadro de diálogo
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpDB expDB = new ExpDB(getApplicationContext());
                expDB.aumentarExp(exp);
                alertDialog.dismiss();
                startNewActivity(DictadosListaActivity.class);
            }
        });
        // Configuración del estilo y visualización del cuadro de diálogo
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
