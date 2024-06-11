package com.example.userapp.core.ui.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

public class AvatarImageView extends AppCompatImageView {

    /*
     * Path of them image to be clipped (to be shown)
     * */
    private Path clipPath;

    /*
     * Place holder drawable (with background color and initials)
     * */
    private Drawable drawable;

    /*
     * Contains initials of the member
     * */
    private String text;

    /*
     * Used to set size and color of the member initials
     * */
    private TextPaint textPaint;

    /*
     * Used as background of the initials with user specific color
     * */
    private Paint paint;

    /*
     * Shape to be drawn
     * */
    private int shape = CIRCLE;

    /*
     * Constants to define shape
     * */
    protected static final int CIRCLE = 0;
    protected static final int RECTANGLE = 1;

    /*
     * We will set it as 2dp
     * */
    private int cornerRadius;

    /*
     * Bounds of the canvas in float
     * Used to set bounds of member initial and background
     * */
    private RectF rectF;

    public AvatarImageView(Context context) {
        super(context);
        init();
    }

    public AvatarImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getAttributes(attrs);
        init();
    }

    public AvatarImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getAttributes(attrs);
        init();
    }

    private void getAttributes(AttributeSet attrs) {
        shape = CIRCLE;
    }

    /*
     * Initialize fields
     * */
    protected void init() {
        rectF = new RectF();
        clipPath = new Path();

        cornerRadius = 20;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#FFFFFF"));
    }

    public void setUser(String name) {
        setUser(name, "");
    }

    public void setUser(String name, String lastName) {
        paint.setColor(Color.parseColor("#3C5BF5"));

        /*
         * Initials of member
         * */
        text = getInitials(name, lastName).toUpperCase();

        setDrawable();
        setImageDrawable(drawable);
    }

    /*
     * Create placeholder drawable
     * */
    private void setDrawable() {
        drawable = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {

                int centerX = Math.round(getBounds().width() * 0.5f);
                int centerY = Math.round(getBounds().height() * 0.5f);

                /*
                 * To draw text
                 * */
                if (text != null) {
                    float textWidth = textPaint.measureText(text) * 0.5f;
                    float textBaseLineHeight = textPaint.getFontMetrics().ascent * -0.4f;

                    /*
                     * Draw the background color before drawing initials text
                     * */
                    if (shape == RECTANGLE) {
                        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
                    } else {
                        canvas.drawCircle(centerX,
                                centerY,
                                Math.max(canvas.getHeight() / 2, textWidth / 2),
                                paint);
                    }

                    /*
                     * Draw the text above the background color
                     * */
                    canvas.drawText(text, centerX - textWidth, centerY + textBaseLineHeight, textPaint);
                }
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.UNKNOWN;
            }
        };
    }

    /*
     * Set the canvas bounds here
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int screenWidth = MeasureSpec.getSize(widthMeasureSpec);
        int screenHeight = MeasureSpec.getSize(heightMeasureSpec);
        rectF.set(0, 0, screenWidth, screenHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        textPaint.setTextSize(getHeight() * .5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (shape == RECTANGLE) {
            clipPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW);
        } else {
            clipPath.addCircle(rectF.centerX(), rectF.centerY(), (rectF.height() / 2), Path.Direction.CW);
        }
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }

    private static String getFirstPrintableSymbol(String text) {
        if (TextUtils.isEmpty(text)) return "";
        int firstSymbol = text.codePointAt(0);
        return new String(Character.toChars(firstSymbol));
    }

    private static String getInitials(String firstName, String lastName) {
        if (firstName != null && firstName.contains(" ") && TextUtils.isEmpty(lastName)) {
            String[] args = firstName.split(" ");
            if (args.length > 1) {
                lastName = args[1];
            }
        }
        return getFirstPrintableSymbol(firstName) + getFirstPrintableSymbol(lastName);
    }
}