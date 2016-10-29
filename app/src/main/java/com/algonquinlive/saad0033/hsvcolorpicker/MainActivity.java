/**
 *  The purpose of the HSV Color Picker is to allow users to choose a color by selecting a Hue
 *  from a range of 0-360 colors, as well the Saturation rate from 1-100% as well how dark or
 *  light the colour is from 1-100%
 *
 *  @author Hassan Saad (saad0033@algonquinlive.com)
 */

package com.algonquinlive.saad0033.hsvcolorpicker;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import model.HSV;
import model.AboutDialogFragment;

public class MainActivity extends AppCompatActivity implements Observer, SeekBar.OnSeekBarChangeListener {

    private TextView mColorSwatch;
    private HSV mModel;
    private SeekBar mHueSB;
    private SeekBar mSaturationSB;
    private SeekBar mValueSB;
    private LinearLayout buttonGroup;
    private static final String ABOUT_DIALOG_TAG;

    private static TextView hueValue;
    private static TextView saturationValue;
    private static TextView valueValue;

    static {
        ABOUT_DIALOG_TAG = "About Dialog";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mModel = new HSV();
        mModel.setHue(HSV.MAX_Hue);
        mModel.setSaturation(HSV.MAX_Saturation);
        mModel.setValueLightness(HSV.MAX_Value_Lightness);

        // reference each View
        mColorSwatch = (TextView) findViewById(R.id.colorSwatch);
        mHueSB = (SeekBar) findViewById(R.id.hueSB);
        mSaturationSB = (SeekBar) findViewById(R.id.saturationSB);
        mValueSB = (SeekBar) findViewById(R.id.valueSB);
        buttonGroup = (LinearLayout) findViewById(R.id.buttonGroup);

        // set initial progress of Seek bars
        mHueSB.setProgress(0);
        mSaturationSB.setProgress(100);
        mValueSB.setProgress(100);

        // register the event handler for each <SeekBar>
        mHueSB.setOnSeekBarChangeListener(this);
        mSaturationSB.setOnSeekBarChangeListener(this);
        mValueSB.setOnSeekBarChangeListener(this);

        // reference text values of Seek bars
        hueValue = (TextView) findViewById(R.id.hueValue);
        saturationValue = (TextView) findViewById(R.id.saturationValue);
        valueValue = (TextView) findViewById(R.id.valueValue);

        buttonHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser == false) {
            return;
        }

        switch (seekBar.getId()) {
            case R.id.hueSB:

                mModel.setHue(mHueSB.getProgress());
                break;
            case R.id.saturationSB:

                mModel.setSaturation(mSaturationSB.getProgress());
                break;
            case R.id.valueSB:

                mModel.setValueLightness(mValueSB.getProgress());
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.hueSB:

                hueValue.setText(mModel.getHue() + "°");
                this.updateColorSwatch();
                break;
            case R.id.saturationSB:

                saturationValue.setText(Math.round(mModel.getSaturation()) + "%");
                this.updateColorSwatch();
                break;
            case R.id.valueSB:

                valueValue.setText(Math.round(mModel.getValue()) + "%");
                this.updateColorSwatch();
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.updateView();
    }

    private void updateColorSwatch() {
        mColorSwatch.setBackgroundColor(Color.HSVToColor(mModel.getColor()));
    }

    // synchronize each View component with the Model
    public void updateView() {
        this.updateColorSwatch();
    }

    public void buttonHandler() {
        for (int i = 0; i < buttonGroup.getChildCount(); i++) {

            View v = buttonGroup.getChildAt(i);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable buttonColor = v.getBackground();

                    // get colour from selected button
                    int color = ((ColorDrawable) buttonColor).getColor();

                    // get hsv float from selected colour
                    float[] hsvColour = new float[]{0, 0, 0};
                    Color.colorToHSV(color, hsvColour);

                    // set progress of Seek bar
                    mHueSB.setProgress(Math.round(hsvColour[0]));
                    mSaturationSB.setProgress(Math.round(hsvColour[1] * 100));
                    mValueSB.setProgress(Math.round(hsvColour[2] * 100));

                    // set current values
                    mModel.setHue(hsvColour[0]);
                    mModel.setSaturation(hsvColour[1] * 100);
                    mModel.setValueLightness(hsvColour[2] * 100);

                    hueValue.setText(hsvColour[0] + "°");
                    saturationValue.setText(hsvColour[1] * 100 + "%");
                    valueValue.setText(hsvColour[2] * 100 + "%");

                    // change selected colour
                    mColorSwatch.setBackgroundColor(Color.HSVToColor(mModel.getColor()));
                }

            });
        }
    }
}
