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

public class DictadosAdapter extends ArrayAdapter<Dictado> {
    private Context context;
    private List<Dictado> dictados;

    public DictadosAdapter(Context context, List<Dictado> dictados) {
        super(context, R.layout.item_dictado, dictados);
        this.context = context;
        this.dictados = dictados;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dictado, parent, false);
        }

        // Obtener el cuento en la posición actual
        Dictado dictado = getItem(position);

        // Configurar el TextView con el título del cuento
        TextView textViewTitulo = convertView.findViewById(R.id.textViewTitulo);
        textViewTitulo.setText(dictado.getTitulo());

        ImageView imageViewIcono = convertView.findViewById(R.id.imageViewIcono);
        Drawable drawable = AppCompatResources.getDrawable(this.getContext(), R.drawable.oido);
        int colorTinte = (dictado.getCompletado() == 0) ? Color.parseColor("#242424") : Color.parseColor("#0065ad");
        DrawableCompat.setTint(drawable, colorTinte);

        imageViewIcono.setImageDrawable(drawable);
        return convertView;
    }
}
