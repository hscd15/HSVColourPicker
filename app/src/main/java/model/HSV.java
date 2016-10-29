package model;

import java.util.Observable;

/**
 * Created by hassansaad on 2016-10-22.
 */

public class HSV extends Observable {

    // CLASS VARIABLES
    public static final float MAX_Hue = 360f * 360 / 360;
    public static final float MAX_Saturation = 100;
    public static final float MAX_Value_Lightness = 100;

    // INSTANCE VARIABLES
    private float hue;
    private float saturation;
    private float valueLightness;
    private float[] hsvColor;

    /**
     * No argument constructor.
     * <p>
     * Instantiate a new instance of this class, and
     * initialize hue, saturation, and value to max values.
     */
    public HSV() {
        this(MAX_Hue, MAX_Saturation, MAX_Value_Lightness);
    }

    public HSV(float hue, float saturation, float valueLightness) {
        super();

        this.hue = hue;
        this.saturation = saturation;
        this.valueLightness = valueLightness;
        this.hsvColor = new float[]{hue, saturation, valueLightness};
    }

    // GETTERS
    public float getHue() {
        return this.hue;
    }

    public float getSaturation() {
        return this.saturation;
    }

    public float getValue() {
        return this.valueLightness;
    }

    public float[] getColor() {
        return hsvColor;
    }

    // SETTERS
    public void setHue(float hue) {
        this.hue = hue;
        this.hsvColor[0] = 360f * hue / 360;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
        this.hsvColor[1] = saturation / 100;
    }

    public void setValueLightness(float valueLightness) {
        this.valueLightness = valueLightness;
        this.hsvColor[2] = valueLightness / 100;
    }
}
