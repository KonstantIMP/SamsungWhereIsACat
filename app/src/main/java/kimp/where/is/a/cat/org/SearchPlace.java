package kimp.where.is.a.cat.org;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Random;

/**
 * View for cat searching
 */
public class SearchPlace extends View {
    private Pair<Float, Float> pointerPosition = null;
    private Pair<Float, Float> catPosition = null;

    private boolean found = false;

    private Bitmap catSprite;

    private final static float LIGHT_RADIUS = 60.0f;
    private final static float MAX_POSITION_DELTA = 15.0f;

    public SearchPlace(@NonNull Context context) { super(context);
        catSprite = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        Toast.makeText(getContext(), "The cat was hidden", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (found) return true;
        pointerPosition = new Pair<>(event.getX(), event.getY());

        float spriteCenterX = catPosition.first + catSprite.getWidth() / 2;
        float spriteCenterY = catPosition.second + catSprite.getHeight() / 2;

        if(Math.abs(spriteCenterX - pointerPosition.first) < MAX_POSITION_DELTA &&
           Math.abs(spriteCenterY - pointerPosition.second) < MAX_POSITION_DELTA) {
            Toast.makeText(getContext(), "You have found the cat!", Toast.LENGTH_LONG).show();
            found = true;
        }

        invalidate(); return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas); canvas.drawColor(Color.WHITE);

        if (catPosition == null) {
            Random random = new Random();
            catPosition = new Pair<>(Math.abs((float)(random.nextInt() % (canvas.getWidth() - catSprite.getWidth()))),
                    Math.abs((float)(random.nextInt() % (canvas.getHeight() - catSprite.getHeight()))));
        }

        canvas.drawBitmap(catSprite, catPosition.first, catPosition.second, null);

        if(found) {
            Paint drawer = new Paint(Paint.ANTI_ALIAS_FLAG);
            drawer.setColor(Color.MAGENTA);

            canvas.drawRect(25.0f, 25.0f, getWidth() - 25.0f, 75.0f, drawer);

            drawer.setColor(Color.WHITE); drawer.setTextSize(14.0f * 3);
            canvas.drawText("Woof", 35.0f, 65.0f, drawer);

            return;
        }

        if (pointerPosition != null) {
            Bitmap shadow = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            shadow.eraseColor(Color.TRANSPARENT);

            Canvas shadowCanvas = new Canvas(shadow);
            shadowCanvas.drawColor(Color.BLACK);

            Paint eraser = new Paint(Paint.ANTI_ALIAS_FLAG);
            eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

            shadowCanvas.drawCircle(pointerPosition.first, pointerPosition.second, LIGHT_RADIUS, eraser);
            canvas.drawBitmap(shadow, 0, 0, null);
        } else canvas.drawColor(Color.BLACK);
    }
}
