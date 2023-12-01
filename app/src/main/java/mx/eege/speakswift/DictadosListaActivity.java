package mx.eege.speakswift;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

/**
 * Actividad que muestra una lista de dictados.
 */
public class DictadosListaActivity extends AppCompatActivity {
    private ListView listViewDictados;
    private List<Dictado> listaDictados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_dictados);

        // Inicializar vistas y datos
        listViewDictados = findViewById(R.id.listViewDictados);
        listaDictados = obtenerListaDeDictados();

        // Configurar el adaptador para la lista
        DictadosAdapter adapter = new DictadosAdapter(this, listaDictados);
        listViewDictados.setAdapter(adapter);

        // Configurar el listener para el clic en un dictado
        listViewDictados.setOnItemClickListener((parent, view, position, id) -> {
            Dictado dictadoSeleccionado = listaDictados.get(position);
            mostrarDetalleDictado(dictadoSeleccionado);
        });

        // Configurar la barra de navegación inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.dictados);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Manejar la selección de elementos de la barra de navegación
                if (item.getItemId() == R.id.cuentos) {
                    startNewActivity(CuentosListaActivity.class);
                    return true;
                } else if (item.getItemId() == R.id.chat) {
                    return true;
                } else if (item.getItemId() == R.id.inicio) {
                    startNewActivity(MainActivity.class);
                    return true;
                } else if (item.getItemId() == R.id.musica) {
                    return true;
                } else if (item.getItemId() == R.id.dictados) {
                    return true;
                }
                return false;
            }
        });
    }

    // Método para obtener la lista de dictados desde la base de datos
    private List<Dictado> obtenerListaDeDictados() {
        DictadosDB dbHelper = new DictadosDB(this);
        return dbHelper.getAllDictados();
    }

    // Método para iniciar una nueva actividad
    private void startNewActivity(Class<?> cls) {
        Intent intent = new Intent(DictadosListaActivity.this, cls);
        startActivity(intent);
    }

    // Método para mostrar los detalles de un dictado
    private void mostrarDetalleDictado(Dictado dictado) {
        Intent intent = new Intent(this, DictadoActivity.class);
        intent.putExtra("dictado", dictado);
        startActivity(intent);
    }
}

