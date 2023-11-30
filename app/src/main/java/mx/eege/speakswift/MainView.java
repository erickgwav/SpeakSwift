package mx.eege.speakswift;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

public class MainView extends View{
    int width, height;
    Paint paint = new Paint();
    Paint textPaint = new Paint();
    Typeface typefaceBold = ResourcesCompat.getFont(getContext(), R.font.montserrat_bold);

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        paint.setColor(Color.argb(255, 0, 101, 173));
        canvas.drawRoundRect((width/2)-300, 50, (width/2)+300, 200, 100.0f, 100.0f, paint);
        paint.setColor(Color.argb(255, 203, 108, 230));
        canvas.drawRoundRect(width/14, height/6, (width/2)-20, 3*(height/8), 50.0f, 50.0f, paint);
        paint.setColor(Color.argb(255, 56, 182, 255));
        canvas.drawRoundRect((width/2)+20, height/6, 13*(width/14), 3*(height/8), 50.0f, 50.0f, paint);
        paint.setColor(Color.argb(255, 255, 87, 87));
        canvas.drawRoundRect(width/14, 3*(height/8)+40, 13*(width/14), 7*(height/12)+40, 50.0f, 50.0f, paint);
        paint.setColor(Color.argb(255, 126, 217, 87));
        canvas.drawRoundRect(width/14, 7*(height/12)+80, 13*(width/14), 19*(height/24)+80, 50.0f, 50.0f, paint);
        textPaint.setTextSize(80);
        textPaint.setTypeface(typefaceBold);
        textPaint.setColor(Color.WHITE);
        canvas.drawText("Inicio", (width/2)-120, 150, textPaint);
    }
}
