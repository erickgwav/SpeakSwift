package mx.eege.speakswift;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.List;

public class CuentosAdapter extends ArrayAdapter<Cuento> {
    private Context context;
    private List<Cuento> cuentos;

    public CuentosAdapter(Context context, List<Cuento> cuentos) {
        super(context, R.layout.item_cuento, cuentos);
        this.context = context;
        this.cuentos = cuentos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cuento, parent, false);
        }

        // Obtener el cuento en la posición actual
        Cuento cuento = getItem(position);

        // Configurar el TextView con el título del cuento
        TextView textViewTitulo = convertView.findViewById(R.id.textViewTitulo);
        textViewTitulo.setText(cuento.getTitulo());

        ImageView imageViewIcono = convertView.findViewById(R.id.imageViewIcono);
        Drawable drawable = AppCompatResources.getDrawable(this.getContext(), R.drawable.ojo);
        int colorTinte = (cuento.getCompletado() == 0) ? Color.parseColor("#242424") : Color.parseColor("#0065ad");
        DrawableCompat.setTint(drawable, colorTinte);

        imageViewIcono.setImageDrawable(drawable);
        return convertView;
    }
}
