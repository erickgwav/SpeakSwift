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

public class DictadosListaActivity extends AppCompatActivity {
    private ListView listViewDictados;
    private List<Dictado> listaDictados;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_dictados);
        listViewDictados = findViewById(R.id.listViewDictados);
        listaDictados = obtenerListaDeDictados();

        // Configurar el adaptador para la lista
        DictadosAdapter adapter = new DictadosAdapter(this, listaDictados);
        listViewDictados.setAdapter(adapter);

        // Configurar el listener para el clic en un cuento
        listViewDictados.setOnItemClickListener((parent, view, position, id) -> {
            Dictado dictadoSeleccionado = listaDictados.get(position);
            mostrarDetalleDictado(dictadoSeleccionado);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.dictados);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

    private List<Dictado> obtenerListaDeDictados() {
        // Utiliza tu DatabaseHelper para obtener la lista de cuentos desde la base de datos
        DictadosDB dbHelper = new DictadosDB(this);
        return dbHelper.getAllDictados();
    }

    private void mostrarDetalleDictado(Dictado dictado) {
        // Inicia una nueva actividad para mostrar los detalles del cuento
        Intent intent = new Intent(this, DictadoActivity.class);
        intent.putExtra("dictado", dictado);
        startActivity(intent);
    }

    private void startNewActivity(Class<?> cls) {
        Intent intent = new Intent(DictadosListaActivity.this, cls);
        startActivity(intent);
    }
}
