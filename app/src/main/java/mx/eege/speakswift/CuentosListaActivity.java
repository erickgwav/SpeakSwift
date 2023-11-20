package mx.eege.speakswift;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CuentosListaActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_cuentos);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.cuentos);
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

    private void startNewActivity(Class<?> cls) {
        // Inicia una nueva Activity
        Intent intent = new Intent(CuentosListaActivity.this, cls);
        startActivity(intent);
        // Opcional: Puedes agregar transiciones de animación entre Activities si lo deseas.
        // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        // Estos recursos de animación deben colocarse en el directorio res/anim.
    }
}
