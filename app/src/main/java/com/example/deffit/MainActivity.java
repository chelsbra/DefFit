package com.example.deffit;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv = (ImageView) findViewById(R.id.image);

        if (iv != null) {
            iv.setOnTouchListener(this);
        }
    }

    public boolean onTouch (View v, MotionEvent ev) {
        boolean handledHere = false;
        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1; // resource id of the next image to display

        // If we cannot find the imageView, return.
        ImageView imageView = (ImageView) v.findViewById (R.id.image);
        if (imageView == null) return false;

        // When the action is Down, see if we should show the "pressed" image for the default image.
        // We do this when the default image is showing. That condition is detectable by looking at the
        // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.
        Integer tagNum = (Integer) imageView.getTag ();
        int currentResource = (tagNum == null) ? R.drawable.anatomy02 : tagNum.intValue ();

        switch (action) {
            case MotionEvent.ACTION_DOWN :
                if (currentResource == R.drawable.anatomy02)
                {
                    nextImage = R.drawable.color_scale;
                    handledHere = true;
                }
                else
                {
                    handledHere = true;
                }
                break;
            case MotionEvent.ACTION_UP :
                // On the UP, we do the click action.
                // The hidden image (image_areas) has three different hotspots on it.
                // The colors are red, blue, and yellow.
                // Use image_areas to determine which region the user touched.
                int touchColor = getHotspotColor (R.id.image_areas, evX, evY);

                ColorTool ct = new ColorTool ();
                int tolerance = 25;

                Resources colorRes = getResources();
                if (ct.closeMatch(colorRes.getColor(R.color.forest), touchColor, tolerance))
                {
                    Toast.makeText(this, "Trap star!", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.red), touchColor, tolerance))
                {
                    Toast.makeText(this, "Boulder shoulder hour!", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.lime_green), touchColor, tolerance))
                {
                    Toast.makeText(this, "Chest sets!", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.blue), touchColor, tolerance))
                {
                    // biceps
                    Toast.makeText(this, "Biceps bro.", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.light_purple), touchColor, tolerance))
                {
                    // forearms
                    Toast.makeText(this, "Popeyes forearm blast", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.mustard), touchColor, tolerance))
                {
                    //obliques
                    Toast.makeText(this, "Oblique freak", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.light_blue), touchColor, tolerance))
                {
                    // core
                    Toast.makeText(this, "Core movements", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.purple), touchColor, tolerance))
                {
                    // quads
                    Toast.makeText(this, "Thunder thighs!", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.maroon), touchColor, tolerance))
                {
                    // inner quad
                    Toast.makeText(this, "inner quad", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.grey), touchColor, tolerance))
                {
                    // rear delts
                    Toast.makeText(this, "Delts give you wings", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.brown), touchColor, tolerance))
                {
                    // upper back
                    Toast.makeText(this, "Bringing sexy back", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.dark_purple), touchColor, tolerance))
                {
                    // lower back
                    Toast.makeText(this, "Dead-light champ", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.dark_orange), touchColor, tolerance))
                {
                    // triceps
                    Toast.makeText(this, "Tricep time", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.light_green), touchColor, tolerance))
                {
                    // glutes
                    Toast.makeText(this, "Bubble butt", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.magenta), touchColor, tolerance))
                {
                    // hamstrings
                    Toast.makeText(this, "Hammy-time oohoohoohh", Toast.LENGTH_LONG).show();
                } else if (ct.closeMatch(colorRes.getColor(R.color.yellow), touchColor, tolerance))
                {
                    // calves
                    Toast.makeText(this, "Moo", Toast.LENGTH_LONG).show();
                }

                handledHere = true;
                break;

            default:
                handledHere = false;
        }

        return  true;
    }

    /**
     * Will get the color from the hotspot image at point x, y
     * @param hotspotId
     * @param x
     * @param y
     * @return x y color
     */
    private int getHotspotColor(int hotspotId, int x, int y) {

        ImageView img = (ImageView) findViewById (hotspotId);
        if (img == null) {
            Log.d ("ImageAreasActivity", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }
}
