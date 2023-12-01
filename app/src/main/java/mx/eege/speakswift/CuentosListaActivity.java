package mx.eege.speakswift;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class CuentosListaActivity extends AppCompatActivity {
    private ListView listViewCuentos;
    private List<Cuento> listaCuentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_cuentos);
        // Inicialización de la ListView y obtención de la lista de cuentos
        listViewCuentos = findViewById(R.id.listViewCuentos);
        listaCuentos = obtenerListaDeCuentos();
        // Configuración del adaptador para la lista
        CuentosAdapter adapter = new CuentosAdapter(this, listaCuentos);
        listViewCuentos.setAdapter(adapter);
        // Configuración del listener para el clic en un cuento
        listViewCuentos.setOnItemClickListener((parent, view, position, id) -> {
            Cuento cuentoSeleccionado = listaCuentos.get(position);
            mostrarDetalleCuento(cuentoSeleccionado);
        });
        // Configuración del BottomNavigationView y su listener para la navegación entre actividades
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.cuentos);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.cuentos) {
                    return true;
                } else if (item.getItemId() == R.id.chat) {
                    return true;
                } else if (item.getItemId() == R.id.inicio) {
                    startNewActivity(MainActivity.class);
                    return true;
                } else if (item.getItemId() == R.id.musica) {
                    return true;
                } else if (item.getItemId() == R.id.dictados) {
                    startNewActivity(DictadosListaActivity.class);
                    return true;
                }
                return false;
            }
        });
    }

    private List<Cuento> obtenerListaDeCuentos() {
        // Utiliza tu DatabaseHelper (CuentosDB) para obtener la lista de cuentos desde la base de datos
        CuentosDB dbHelper = new CuentosDB(this);
        return dbHelper.getAllCuentos();
    }

    private void mostrarDetalleCuento(Cuento cuento) {
        // Inicia una nueva actividad para mostrar los detalles del cuento
        Intent intent = new Intent(this, CuentoActivity.class);
        intent.putExtra("cuento", cuento);
        startActivity(intent);
    }

    private void startNewActivity(Class<?> cls) {
        // Método para iniciar una nueva actividad
        Intent intent = new Intent(CuentosListaActivity.this, cls);
        startActivity(intent);
    }
}

