package com.matching.tech.bio.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.innovatrics.commons.geom.Point;

/**
 * OverlayView.java  - OverlayView is a view object used by the surface view.
 * @author Shravan Nitta
 * @version 1.0
 */
public class OverlayView extends View {
    private Rect[] rectangles = null;
    private Paint[] rectanglesPaints = null;

    private RectF[] ovals = null;
    private Paint[] ovalsPaints = null;

    private String[] texts = null;
    private Paint[] textsPaints = null;
    private Point[] textsPositions = null;


    private Bitmap targetBMP = null;

    public OverlayView(Context c, AttributeSet attr) {
        super(c, attr);        
    }

    /**
     *  onDraw(Canvas canvas) is android call back method to draw image in the preview.
     *
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if ( targetBMP != null ) {
            canvas.drawBitmap(targetBMP, null, new Rect(0,0, getWidth(), getHeight()), null);
        }


        if(rectangles  != null){
            for(int i = 0; i < rectangles.length; i++) {
                canvas.drawRect(rectangles[i], rectanglesPaints[i]);
            }
        }

        if (ovals != null) {
            for(int i = 0; i < ovals.length; i++) {
                canvas.drawOval(ovals[i], ovalsPaints[i]);
            }
        }

        if (texts != null) {
            for(int i = 0; i < texts.length; i++) {
                canvas.drawText(texts[i], textsPositions[i].getX(), textsPositions[i].getY(), textsPaints[i]);
            }
        }
    }
    /**
     *  onMeasure(int widthMeasureSpec, int heightMeasureSpec) is android call back method to measure the preview width and height.
     *
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
