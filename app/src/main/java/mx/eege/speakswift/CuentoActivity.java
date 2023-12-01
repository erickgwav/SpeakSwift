package mx.eege.speakswift;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CuentoActivity extends AppCompatActivity {
    private TextView tituloTxt;
    private ImageView imagenImg;
    private TextView contenidoTxt;
    private TextView pregunta1Txt;
    private TextView answ1_1Txt;
    private TextView answ1_2Txt;
    private TextView answ1_3Txt;
    private TextView pregunta2Txt;
    private TextView answ2_1Txt;
    private TextView answ2_2Txt;
    private TextView answ2_3Txt;
    private TextView pregunta3Txt;
    private TextView answ3_1Txt;
    private TextView answ3_2Txt;
    private TextView answ3_3Txt;

    private int correct = 0;
    private int exp = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuento);

        // Inicialización de elementos de la interfaz
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.cuentos);

        // Configuración del BottomNavigationView y su listener para la navegación entre actividades
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

        Cuento cuento = getIntent().getParcelableExtra("cuento");
        int id = cuento.getId();
        String titulo = cuento.getTitulo();
        String contenido = cuento.getContenido();
        String imagen = cuento.getImagen();
        String pregunta1 = cuento.getPregunta1();
        String answ1_1 = cuento.getAnsw1_1();
        String answ1_2 = cuento.getAnsw1_2();
        String answ1_3 = cuento.getAnsw1_3();
        String answ1 = cuento.getAnsw1();
        String pregunta2 = cuento.getPregunta2();
        String answ2_1 = cuento.getAnsw2_1();
        String answ2_2 = cuento.getAnsw2_2();
        String answ2_3 = cuento.getAnsw2_3();
        String answ2 = cuento.getAnsw2();
        String pregunta3 = cuento.getPregunta3();
        String answ3_1 = cuento.getAnsw3_1();
        String answ3_2 = cuento.getAnsw3_2();
        String answ3_3 = cuento.getAnsw3_3();
        String answ3 = cuento.getAnsw3();

        tituloTxt = findViewById(R.id.titulo);
        tituloTxt.setText(titulo);
        imagenImg = findViewById(R.id.imagen);
        imagenImg.setImageResource(getResources().getIdentifier(imagen, "drawable", getPackageName()));
        contenidoTxt = findViewById(R.id.contenido);
        contenidoTxt.setText(leerContenidoArchivo(contenido));
        pregunta1Txt = findViewById(R.id.pregunta1);
        pregunta1Txt.setText(pregunta1);
        answ1_1Txt = findViewById(R.id.answ1_1);
        answ1_1Txt.setText(answ1_1);
        answ1_2Txt = findViewById(R.id.answ1_2);
        answ1_2Txt.setText(answ1_2);
        answ1_3Txt = findViewById(R.id.answ1_3);
        answ1_3Txt.setText(answ1_3);
        pregunta2Txt = findViewById(R.id.pregunta2);
        pregunta2Txt.setText(pregunta2);
        answ2_1Txt = findViewById(R.id.answ2_1);
        answ2_1Txt.setText(answ2_1);
        answ2_2Txt = findViewById(R.id.answ2_2);
        answ2_2Txt.setText(answ2_2);
        answ2_3Txt = findViewById(R.id.answ2_3);
        answ2_3Txt.setText(answ2_3);
        pregunta3Txt = findViewById(R.id.pregunta3);
        pregunta3Txt.setText(pregunta3);
        answ3_1Txt = findViewById(R.id.answ3_1);
        answ3_1Txt.setText(answ3_1);
        answ3_2Txt = findViewById(R.id.answ3_2);
        answ3_2Txt.setText(answ3_2);
        answ3_3Txt = findViewById(R.id.answ3_3);
        answ3_3Txt.setText(answ3_3);

        RadioGroup radioGroup1 = findViewById(R.id.radioGroup1);
        RadioGroup radioGroup2 = findViewById(R.id.radioGroup2);
        RadioGroup radioGroup3 = findViewById(R.id.radioGroup3);

        Button submitButton = findViewById(R.id.submit);

        // Configuración del listener para el botón de envío de respuestas
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct = 0;
                int selectedRadioButtonId1 = radioGroup1.getCheckedRadioButtonId();
                int selectedRadioButtonId2 = radioGroup2.getCheckedRadioButtonId();
                int selectedRadioButtonId3 = radioGroup3.getCheckedRadioButtonId();

                // Verificar si se seleccionó una opción en cada pregunta
                if (selectedRadioButtonId1 != -1 && selectedRadioButtonId2 != -1 && selectedRadioButtonId3 != -1) {
                    String valorSeleccionado1 = getResources().getResourceEntryName(selectedRadioButtonId1);
                    String valorSeleccionado2 = getResources().getResourceEntryName(selectedRadioButtonId2);
                    String valorSeleccionado3 = getResources().getResourceEntryName(selectedRadioButtonId3);

                    // Verificar respuestas y calcular puntaje
                    if(valorSeleccionado1.equals(answ1)) correct++;
                    if(valorSeleccionado2.equals(answ2)) correct++;
                    if(valorSeleccionado3.equals(answ3)) correct++;

                    // Asignar experiencia según el puntaje obtenido y actualizar en la base de datos
                    if(correct >= 3) {
                        exp = 30;
                        CuentosDB cuentosDB = new CuentosDB(getApplicationContext());
                        cuentosDB.actualizarCompletado(cuento.getId(), 1);
                    } else if(correct == 2) {
                        exp = 15;
                    } else if(correct == 1) {
                        exp = 5;
                    }
                    showResultsDialog(correct, exp);
                } else {
                    // Mostrar un mensaje si no se seleccionó una opción en cada pregunta
                    Toast.makeText(getApplicationContext(), "Selecciona una opción en cada pregunta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para mostrar un mensaje Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Método para iniciar una nueva actividad
    private void startNewActivity(Class<?> cls) {
        Intent intent = new Intent(CuentoActivity.this, cls);
        startActivity(intent);
    }

    // Método para leer el contenido de un archivo
    private String leerContenidoArchivo(String ruta) {
        StringBuilder contenido = new StringBuilder();
        try {
            InputStream inputStream = getAssets().open(ruta);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            // Leer el contenido línea por línea y agregarlo al StringBuilder
            while ((line = bufferedReader.readLine()) != null) {
                contenido.append(line).append("\n");
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenido.toString();
    }

    // Método para mostrar un cuadro de diálogo con los resultados de la actividad
    public void showResultsDialog(int correct, int exp){
        AlertDialog.Builder builder =
                new AlertDialog.Builder
                        (CuentoActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(CuentoActivity.this).inflate(
                R.layout.layout_reward_dialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        // Configuración del cuadro de diálogo según el puntaje obtenido
        if (correct >= 3) {
            ((TextView) view.findViewById(R.id.textTitle))
                    .setText("¡Excelente trabajo!");
            ((TextView) view.findViewById(R.id.textMessage))
                    .setText("Has superado con éxito esta actividad. ¡Sigue así!");
        } else if (correct == 2) {
            ((TextView) view.findViewById(R.id.textTitle))
                    .setText("¡Casi lo logras!");
            ((TextView) view.findViewById(R.id.textMessage))
                    .setText("Has respondido correctamente " + correct + " de 3 preguntas. Tienes el potencial, ¡inténtalo de nuevo!");
        } else {
            ((TextView) view.findViewById(R.id.textTitle))
                    .setText("¡Ups!");
            ((TextView) view.findViewById(R.id.textMessage))
                    .setText("Has respondido correctamente " + correct + " de 3 preguntas. No te preocupes, sigue practicando y mejorarás.");
        }
        // Configuración del botón en el cuadro de diálogo para obtener experiencia
        ((Button) view.findViewById(R.id.buttonAction))
                .setText("Obtener +" + exp + " EXP");
        final AlertDialog alertDialog = builder.create();
        // Configuración del listener para el botón en el cuadro de diálogo
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aumentar la experiencia en la base de datos y cerrar el cuadro de diálogo
                ExpDB expDB = new ExpDB(getApplicationContext());
                expDB.aumentarExp(exp);
                alertDialog.dismiss();
                startNewActivity(CuentosListaActivity.class);
            }
        });
        // Configuración del fondo del cuadro de diálogo
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        // Mostrar el cuadro de diálogo
        alertDialog.show();
    }
}
